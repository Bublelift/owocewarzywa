package com.example.owocewarzywa.practice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.owocewarzywa.R
import com.example.owocewarzywa.model.PracticeViewModel

class PracticeSelect : Fragment() {

    private val practiceData: PracticeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Toast.makeText(requireContext(), practiceData.language.value, Toast.LENGTH_SHORT).show()
        Toast.makeText(requireContext(), practiceData.topic.value, Toast.LENGTH_SHORT).show()
        Toast.makeText(requireContext(), practiceData.difficulty.value, Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_practice_select, container, false)
    }
}