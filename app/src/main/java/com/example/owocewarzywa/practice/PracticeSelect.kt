package com.example.owocewarzywa.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentPracticeSelectBinding
import com.example.owocewarzywa.model.PracticeViewModel

class PracticeSelect : Fragment() {

    private val practiceData: PracticeViewModel by activityViewModels()

    private var binding: FragmentPracticeSelectBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPracticeSelectBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.buttonQuiz.setOnClickListener{
            goSettings()
        }
        binding!!.buttonFiszki.setOnClickListener{
            goSettings()
        }
        binding!!.buttonLuki.setOnClickListener{
            goSettings()
        }
        binding!!.buttonMemory.setOnClickListener{
            goSettings()
        }
        binding!!.buttonUnscramble.setOnClickListener{
            goSettings()
        }
        binding!!.buttonRiddles.setOnClickListener{
            goSettings()
        }
    }

    fun goSettings() {
        findNavController().navigate(R.id.action_practiceSelect_to_practiceSettingsFragment)
    }
}