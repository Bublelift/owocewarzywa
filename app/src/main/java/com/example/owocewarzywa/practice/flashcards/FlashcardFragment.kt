package com.example.owocewarzywa.practice.flashcards

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentFlashcardBinding
import com.example.owocewarzywa.utils.GlideApp
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_login.*


class FlashcardFragment : Fragment() {

    private lateinit var binding: FragmentFlashcardBinding

    private val viewModel: FlashcardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlashcardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flashcardViewModel = viewModel
        binding.maxNoOfCards = 10
        binding.lifecycleOwner = viewLifecycleOwner
        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI
        binding.score.text = String.format("Wynik: %d", viewModel.score.value)
        binding.wordCount.text = String.format(
            "%d z %d słów", viewModel.currentWordCount.value, 10)
        binding.flashcardHint1.text = "Kategoria: "+ viewModel.currentCategory.value
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
        when (viewModel.hints.value) {
            2 -> {
                binding.flashcardHint1.visibility = View.VISIBLE
                binding.submit.layoutParams = setTopMargin(30)
                binding.skip.text = "Podpowiedź (1)"
                viewModel.hints.value = 1
            }
            1 -> {
                renderImageHint(viewModel.currentImage.value!!)
                binding.submit.layoutParams = setTopMargin(10)
                binding.flashcardHint2.visibility = View.VISIBLE
                binding.skip.text = "Pomiń"
                viewModel.hints.value = 0
            }
            0 -> {
                if (viewModel.nextWord()) {
                    setErrorTextField(false)
                    updateNextWordOnScreen()
                } else {
                    showFinalScoreDialog()
                }
            }
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
        viewModel.hints.value = 2
        binding.skip.text = "Podpowiedź (2)"
        binding.flashcardHint1.text = "Kategoria: " + viewModel.currentCategory.value
        binding.flashcardHint1.visibility = View.GONE
        binding.flashcardHint2.visibility = View.GONE
        binding.score.text = String.format("Wynik: %d", viewModel.score.value)
        binding.wordCount.text = String.format(
            "%d z %d słów", viewModel.currentWordCount.value, 10)
        binding.textViewUnscrambledWord.text = viewModel.currentFlashWord.value
    }

    private fun renderImageHint(url: String) {
        GlideApp.with(this)
            .load(url)
            .error(R.drawable.ic_error)
            .into(binding.flashcardHint2)
    }

    private fun setTopMargin(margin: Int): ConstraintLayout.LayoutParams {
        val margindp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, margin.toFloat(), resources
                .displayMetrics
        ).toInt()
        val layoutParams = binding.submit.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.setMargins(0, margindp, 0, 0)
        return layoutParams
    }
}