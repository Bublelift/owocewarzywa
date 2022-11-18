package com.example.owocewarzywa.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentRegisterBinding
import com.example.owocewarzywa.model.OrderViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        auth = FirebaseAuth.getInstance()
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.registerFragment = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun go_to_login() {
        findNavController().navigateUp()
    }

    fun register() {
        val email = binding!!.emailInput.text.toString()
        val password = binding!!.passwordInput.text.toString()
        if (email == "" || password.toCharArray().size < 6)
            if (email == "") binding!!.email.setError("Email nie może być pusty")
            else binding!!.password.setError("Hasło nie może być krótsze niż 6 znaków")
        else {
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    findNavController().navigate(R.id.action_registerFragment_to_startFragment)
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }
}