package com.example.owocewarzywa.practice.fill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentFillBinding
import com.example.owocewarzywa.model.PracticeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class FillFragment : Fragment() {

    private val FillViewModel: FillViewModel by viewModels()
    private val practiceData: PracticeViewModel by activityViewModels()
    private lateinit var binding: FragmentFillBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {initView()}
        binding.fillSubmit.setOnClickListener{ endGame() }
    }

    private suspend fun initView() {
        FillViewModel.initFill(practiceData.language.value.toString(),
            practiceData.difficulty.value.toString(),
            practiceData.topic.value.toString())
        onAPISuccess()
    }

    private fun onAPISuccess() {
        binding!!.fillContent.text = FillViewModel.fillText.value
        initSpinners()
    }

    private fun initSpinners() {
        val langDataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, FillViewModel.answerList.value!!.toMutableList())
        langDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding!!.fill1.adapter = langDataAdapter;
        binding!!.fill2.adapter = langDataAdapter;
        binding!!.fill3.adapter = langDataAdapter;

    }

    private fun endGame() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Gratulacje!")
            .setMessage(String.format("Wynik: %d", getScore()))
            .setCancelable(false)
            .setPositiveButton("Menu główne") { _, _ ->
                findNavController().navigate(R.id.action_fillFragment_to_startFragment)
            }
            .show()
    }

    private fun getScore(): Int {
        var score = 0
        if (binding.fill1.selectedItem.toString() == FillViewModel.word1.value) score += 50
        if (binding.fill2.selectedItem.toString() == FillViewModel.word2.value) score += 50
        if (binding.fill3.selectedItem.toString() == FillViewModel.word3.value) score += 50
        return score
    }
}