package com.example.owocewarzywa.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentLoginBinding
import com.example.owocewarzywa.model.OrderViewModel

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.loginFragment = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun login() {
        Log.i("login","")
        sharedViewModel.log_in("kleszko1")
        findNavController().navigate(R.id.action_loginFragment_to_startFragment)
    }

}