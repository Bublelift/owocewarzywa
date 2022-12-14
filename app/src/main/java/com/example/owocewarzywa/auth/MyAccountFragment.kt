package com.example.owocewarzywa.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.owocewarzywa.R
import com.example.owocewarzywa.model.OrderViewModel
import com.example.owocewarzywa.utils.FirestoreUtil
import com.example.owocewarzywa.utils.GlideApp
import com.example.owocewarzywa.utils.StorageUtil
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream



class MyAccountFragment : Fragment() {

    private var nickname: EditText? = null
    private var bio: EditText? = null
    private var profile_pic: ImageView? = null
    private var score: TextView? = null
    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChanged = false
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_account, container, false)
        view.apply {
            nickname = findViewById<EditText>(R.id.nickname_input)
            bio = findViewById<EditText>(R.id.bio_input)
            profile_pic = findViewById<ImageView>(R.id.profile_pic)
            score = findViewById(R.id.your_score)
            findViewById<ImageView>(R.id.profile_pic).setOnClickListener {
                val intent = Intent().apply{
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                    putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
                }
                startActivityForResult(Intent.createChooser(intent, "Select Image"), RC_SELECT_IMAGE)
            }
            findViewById<Button>(R.id.save).setOnClickListener {
                if (findViewById<EditText>(R.id.nickname_input).text.toString().length < 1)
                    findViewById<EditText>(R.id.nickname_input).setError("Podaj nazw?? u??ytkownika")
                else {
                    if (::selectedImageBytes.isInitialized) StorageUtil.uploadProfilePhoto(
                        selectedImageBytes
                    ) { imagePath ->
                        FirestoreUtil.updateCurrentUser(
                            findViewById<EditText>(R.id.nickname_input).text.toString(),
                            findViewById<EditText>(R.id.bio_input).text.toString(), imagePath
                        )
                    }
                    else {
                        FirestoreUtil.updateCurrentUser(
                            findViewById<EditText>(R.id.nickname_input).text.toString(),
                            findViewById<EditText>(R.id.bio_input).text.toString(), null
                        )
                    }
                    Toast.makeText(requireContext(), "Zapisano zmiany", Toast.LENGTH_SHORT).show()
                }
            }
            findViewById<Button>(R.id.signout).setOnClickListener{
                FirebaseAuth.getInstance().signOut()
                sharedViewModel.log_in("")
                findNavController().navigateUp()
            }
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()

            GlideApp.with(this).load(selectedImageBmp).placeholder(R.drawable.no_profile)
                .fallback(R.drawable.no_profile)
                .error(R.drawable.no_profile).into(profile_pic!!)

            pictureJustChanged = true
        }
    }

    override fun onStart() {
        super.onStart()
        FirestoreUtil.getCurrentUser { user ->
            if (this@MyAccountFragment.isVisible) {
                nickname!!.setText(user.name)
                bio!!.setText(user.bio)
                score!!.setText(getString(R.string.your_score) + " " +(user.score?.toString() ?: "0"))
                if (!pictureJustChanged && user.profilePicturePath != null)
                    GlideApp.with(this)
                        .load(StorageUtil.pathToReference(user.profilePicturePath))
                        .placeholder(R.drawable.no_profile)
                        .fallback(R.drawable.no_profile)
                        .error(R.drawable.no_profile)
                        .into(profile_pic!!)
            }
        }
    }
}