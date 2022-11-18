/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.owocewarzywa

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.databinding.FragmentStartBinding
import com.example.owocewarzywa.model.OrderViewModel
import com.example.owocewarzywa.utils.FirestoreUtil
import com.example.owocewarzywa.webrtc.LobbyActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * This is the first screen of the Cupcake app. The user can choose how many cupcakes to order.
 */
class StartFragment : Fragment() {


    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentStartBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding?.startFragment = this
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            startFragment = this@StartFragment
        }
        (activity as MainActivity).supportActionBar?.show()
        val user_logged_in = sharedViewModel.logged_in.value ?: 0
        Log.e("CZY ZALOGOWANO",sharedViewModel.logged_in.value.toString())
        if (user_logged_in == 0 || user_logged_in == "") {
            findNavController().navigate(R.id.action_startFragment_to_loginFragment)
        }
        FirestoreUtil.getCurrentUser { user ->
            if (user.name == null) findNavController().navigate(R.id.action_startFragment_to_myAccountFragment)
        }

    }

    /**
     * Start an order with the desired quantity of cupcakes and navigate to the next screen.
     */
    fun orderCupcake(quantity: Int) {
        // Update the view model with the quantity
//        sharedViewModel.setQuantity(quantity)
//
//        // If no flavor is set in the view model yet, select vanilla as default flavor
//        if (sharedViewModel.hasNoFlavorSet()) {
//            sharedViewModel.setFlavor(getString(R.string.vanilla))
//        }

        // Navigate to the next destination to select the flavor of the cupcakes
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun goVoiceCall() {

        activity?.let{
            val intent = Intent (it, LobbyActivity::class.java)
            it.startActivity(intent)
        }
    }

    fun goProfile() {
        findNavController().navigate(R.id.action_startFragment_to_myAccountFragment)
    }

    fun goPeople() {
        findNavController().navigate(R.id.action_startFragment_to_peopleFragment)
    }

    fun goPractice() {
        findNavController().navigate(R.id.action_startFragment_to_practiceSelect)
    }
}