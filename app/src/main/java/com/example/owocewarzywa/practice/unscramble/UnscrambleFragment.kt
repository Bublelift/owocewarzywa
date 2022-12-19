package com.example.owocewarzywa.practice.unscramble

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentUnscrambleBinding
import com.example.owocewarzywa.model.PracticeViewModel
import com.example.owocewarzywa.utils.FirestoreUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

/**
 * Fragment where the game is played, contains the game logic.
 */
class UnscrambleFragment : Fragment() {

    private val viewModel: UnscrambleViewModel by viewModels()
    private val practiceData: PracticeViewModel by activityViewModels()

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: FragmentUnscrambleBinding

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same com.example.android.unscramble.ui.game.GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = FragmentUnscrambleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = 10
        binding.lifecycleOwner = viewLifecycleOwner
        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        viewLifecycleOwner.lifecycleScope.launch {initView()}

    }

    private suspend fun initView(){
        viewModel.initUnscramble(
            practiceData.language.value.toString(),
            practiceData.difficulty.value.toString(),
            practiceData.topic.value.toString()
        )
        binding.score.text = String.format("Wynik: %d", viewModel.score.value)
        binding.wordCount.text = String.format(
            "%d z %d słów", viewModel.currentWordCount.value, binding.maxNoOfWords)
        updateNextWordOnScreen()
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    * After the last word, the user is shown a Dialog with the final score.
    */
    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (viewModel.nextWord()) {
                updateNextWordOnScreen()
            } else {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    /*
    * Skips the current word without changing the score.
    */
    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
            updateNextWordOnScreen()
        } else {
            showFinalScoreDialog()
        }
    }

    /*
    * Creates and shows an AlertDialog with the final score.
    */
    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Gratulacje!")
            .setMessage(String.format("Wynik: %d", viewModel.score.value))
            .setCancelable(false)
            .setPositiveButton("Menu główne") { _, _ ->
                FirestoreUtil.updateUserScore(viewModel.score.value!!)
                goMenu()
            }
            .show()
    }

    private fun goMenu() {
        //practiceData.resetAll()
        findNavController().navigate(R.id.action_unscrambleFragment_to_startFragment)
    }


    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = "Spróbuj ponownie"
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*
     * Displays the next scrambled word on screen.
     */
    private fun updateNextWordOnScreen() {
        binding.score.text = String.format("Wynik: %d", viewModel.score.value)
        binding.wordCount.text = String.format(
            "%d z %d słów", viewModel.currentWordCount.value, binding.maxNoOfWords)
        binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord.value
    }
}