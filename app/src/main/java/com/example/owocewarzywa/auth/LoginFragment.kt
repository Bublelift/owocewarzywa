package com.example.owocewarzywa.auth

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentLoginBinding
import com.example.owocewarzywa.model.OrderViewModel
import com.example.owocewarzywa.MainActivity
import com.example.owocewarzywa.chat.FirestoreUtil
import com.example.owocewarzywa.model.AuthViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    //private val authViewModel: AuthViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        auth = FirebaseAuth.getInstance()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.loginFragment = this
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun go_to_register() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

//    fun login() {
//        Log.i("login","")
//        sharedViewModel.log_in("kleszko1")
//        findNavController().navigate(R.id.action_loginFragment_to_startFragment)
//    }

    fun login() {
        val email = binding!!.emailInput.text.toString()
        val password = binding!!.passwordInput.text.toString()
        if (email == "") binding!!.email.setError("Email nie może być pusty")
        else if (password == "") binding!!.password.setError("Hasło nie może być puste")
        else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FirestoreUtil.initCurrentUserIfFirstTime {
                        sharedViewModel.log_in(auth.currentUser!!.uid)
                        Log.e("LOGIN_PROCESS", "przekierowanie")
                        findNavController().navigate(R.id.action_loginFragment_to_startFragment)
                    }

                }
            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun google_login() {
        //Logowanie za pomocą konta Google
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(requireActivity()) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, 0,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("google_login1", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(requireActivity()) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d("google_login2", e.localizedMessage)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
        val idToken = googleCredential.googleIdToken
        when {
            idToken != null -> {
                // Got an ID token from Google. Use it to authenticate
                // with Firebase.
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success")
//                            val user = auth.currentUser
//                            updateUI(user)
                            findNavController().navigate(R.id.action_loginFragment_to_startFragment)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("OnActivityResult1", "signInWithCredential:failure", task.exception)
                        }
                    }
            }
            else -> {
                // Shouldn't happen.
                Log.d("onActivityResult2", "No ID token!")
            }
        }
    }




//    // Configure Google Sign In inside onCreate mentod
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(requireContext().getString(R.string.client_id))
//        .requestEmail()
//        .build()
//    // getting the value of gso inside the GoogleSigninClient
//    mGoogleSignInClient = GoogleSignIn.getClient(this,gso)


}