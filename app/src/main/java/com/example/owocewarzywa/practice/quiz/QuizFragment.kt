package com.example.owocewarzywa.practice.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentQuizBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding

    private val quizViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch { initQuiz() }
        binding.quizSubmit.setOnClickListener { endGame() }
    }

    private suspend fun initQuiz() {
        quizViewModel.initQuiz()
        binding.quizQuestion1.text = quizViewModel.question1.value
        with(quizViewModel.q1answers.value!!) {
            binding.quizAnswer1a.text = this[0]
            binding.quizAnswer1b.text = this[1]
            binding.quizAnswer1c.text = this[2]
        }
        binding.quizQuestion2.text = quizViewModel.question2.value
        with(quizViewModel.q2answers.value!!) {
            binding.quizAnswer2a.text = this[0]
            binding.quizAnswer2b.text = this[1]
            binding.quizAnswer2c.text = this[2]
        }
        binding.quizQuestion3.text = quizViewModel.question3.value
        with(quizViewModel.q3answers.value!!) {
            binding.quizAnswer3a.text = this[0]
            binding.quizAnswer3b.text = this[1]
            binding.quizAnswer3c.text = this[2]
        }
    }

    private fun endGame() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Gratulacje!")
            .setMessage(String.format("Wynik: %d", 69))
            .setCancelable(false)
            .setPositiveButton("Menu główne") { _, _ ->
                findNavController().navigate(R.id.action_quizFragment_to_startFragment)
            }
            .show()
    }

}