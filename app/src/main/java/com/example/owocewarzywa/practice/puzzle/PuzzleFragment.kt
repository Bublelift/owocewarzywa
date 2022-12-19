package com.example.owocewarzywa.practice.puzzle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentPuzzleBinding
import com.example.owocewarzywa.model.PracticeViewModel
import com.example.owocewarzywa.utils.FirestoreUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class PuzzleFragment : Fragment() {

    private lateinit var binding: FragmentPuzzleBinding
    private val practiceData: PracticeViewModel by activityViewModels()
    private val quizViewModel: PuzzleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPuzzleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.quizSubmit.visibility = View.INVISIBLE
        viewLifecycleOwner.lifecycleScope.launch { initQuiz() }
        quizViewModel.apiStatus.observe(viewLifecycleOwner) {
            if (it == "Success") binding.quizSubmit.visibility = View.VISIBLE
        }
        binding.quizSubmit.setOnClickListener { endGame() }
    }

    private suspend fun initQuiz() {
        quizViewModel.initQuiz(
            practiceData.difficulty.value.toString(),
            practiceData.language.value.toString(),
            practiceData.topic.value.toString()
        )
        binding.quizQuestion1.text = quizViewModel.questions.value!![0]
        with(quizViewModel.q1answers.value!!) {
            binding.quizAnswer1a.text = this[0]
            binding.quizAnswer1b.text = this[1]
            binding.quizAnswer1c.text = this[2]
        }
        binding.quizQuestion2.text = quizViewModel.questions.value!![1]
        with(quizViewModel.q2answers.value!!) {
            binding.quizAnswer2a.text = this[0]
            binding.quizAnswer2b.text = this[1]
            binding.quizAnswer2c.text = this[2]
        }
        binding.quizQuestion3.text = quizViewModel.questions.value!![2]
        with(quizViewModel.q3answers.value!!) {
            binding.quizAnswer3a.text = this[0]
            binding.quizAnswer3b.text = this[1]
            binding.quizAnswer3c.text = this[2]
        }
    }

    private fun endGame() {
        var score = 0
        if (requireActivity().findViewById<RadioButton>(binding.quizQuestion1answers.checkedRadioButtonId).text == quizViewModel.correctAnswers.value!![0]) score += 50
        if (requireActivity().findViewById<RadioButton>(binding.quizQuestion2answers.checkedRadioButtonId).text == quizViewModel.correctAnswers.value!![1]) score += 50
        if (requireActivity().findViewById<RadioButton>(binding.quizQuestion3answers.checkedRadioButtonId).text == quizViewModel.correctAnswers.value!![2]) score += 50
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Gratulacje!")
            .setMessage(String.format("Wynik: %d", score))
            .setCancelable(false)
            .setPositiveButton("Menu główne") { _, _ ->
                FirestoreUtil.updateUserScore(score)
                findNavController().navigate(R.id.action_puzzleFragment_to_startFragment)
            }
            .show()
    }
}