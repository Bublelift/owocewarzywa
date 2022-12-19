package com.example.owocewarzywa.practice.memo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentMemoBinding
import com.example.owocewarzywa.model.PracticeViewModel
import com.example.owocewarzywa.utils.FirestoreUtil
import com.example.owocewarzywa.utils.GlideApp
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import kotlin.concurrent.schedule
import java.util.Timer

class MemoFragment : Fragment() {

    private val viewModel: MemoViewModel by viewModels()
    private val practiceData: PracticeViewModel by activityViewModels()
    private lateinit var binding: FragmentMemoBinding
    private lateinit var cards: List<ImageView>
    private lateinit var reverses: List<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.score.text = String.format("Wynik: %d", viewModel.score.value)
        binding.memoLabel.text = "Zapamiętaj ułożenie obrazków"
        binding.memoGoal.visibility = View.GONE
        cards = listOf(binding.memoPic1,
            binding.memoPic2,
            binding.memoPic3,
            binding.memoPic4,
            binding.memoPic5,
            binding.memoPic6,
            binding.memoPic7,
            binding.memoPic8,
            binding.memoPic9,
            binding.memoPic10)
        reverses = listOf(binding.memoReverse1,
            binding.memoReverse2,
            binding.memoReverse3,
            binding.memoReverse4,
            binding.memoReverse5,
            binding.memoReverse6,
            binding.memoReverse7,
            binding.memoReverse8,
            binding.memoReverse9,
            binding.memoReverse10)
        viewLifecycleOwner.lifecycleScope.launch {initView()}
        binding.buttonReady.setOnClickListener{ beginMemo() }
        reverses.forEach { it.visibility = View.INVISIBLE }
    }


    private fun pickCard(card: ImageView) {
        binding.barrier.visibility = View.VISIBLE
        viewModel.tryGuess()
        val index = reverses.indexOf(card)
        card.visibility = View.INVISIBLE
        if (viewModel.currentGoal.value!! == viewModel.cardsList.value!![index].name) {
            Timer("Display Reverse back", false).schedule(1500) {
                requireActivity().runOnUiThread {
                    cards[index].foreground = requireContext().getDrawable(R.drawable.check)
                    viewModel.addScore()
                    binding.score.text = "Wynik: " + viewModel.score.value.toString()
                    if (viewModel.tryGetNextGoal() == false) endGame()
                    else binding.memoGoal.text = viewModel.currentGoal.value
                    binding.barrier.visibility = View.INVISIBLE
                }
            }
        }
        else {
            Timer("Display Reverse back", false).schedule(1500) {
                requireActivity().runOnUiThread {
                    cards[index].foreground = requireContext().getDrawable(R.drawable.cross)
                    Toast.makeText(requireContext(), "Spróbuj ponownie", Toast.LENGTH_SHORT).show()
                }
            }


            Timer("Display Reverse back", false).schedule(3000) {
                requireActivity().runOnUiThread {
                    cards[index].foreground = null
                    card.visibility = View.VISIBLE
                    binding.barrier.visibility = View.INVISIBLE
                }
            }
        }
    }

    private suspend fun initView() {
        viewModel.initMemo(
            practiceData.language.value.toString(),
            practiceData.difficulty.value.toString(),
            practiceData.topic.value.toString()
        )
        for (i in 0..viewModel.cardsList.value!!.size - 1) {
            GlideApp.with(this)
                .load(viewModel.cardsList.value!![i].image)
                .error(R.drawable.ic_error)
                .into(cards[i])
        }
        reverses.forEach { it.setOnClickListener{ pickCard(it as ImageView) } }
        binding.barrier.visibility = View.INVISIBLE
        binding.memoloading.visibility = View.INVISIBLE
        Log.e("fragment-result", viewModel.cardsList.value.toString())
    }


    private fun endGame() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Gratulacje!")
            .setMessage(String.format("Wynik: %d", viewModel.score.value))
            .setCancelable(false)
            .setPositiveButton("Menu główne") { _, _ ->
                FirestoreUtil.updateUserScore(viewModel.score.value!!)
                findNavController().navigate(R.id.action_memoFragment_to_startFragment)
            }
            .show()
    }

    private fun beginMemo() {
        binding.memoLabel.text = "Wskaż kartę zawierającą:"
        binding.memoGoal.text = viewModel.currentGoal.value
        binding.memoGoal.visibility = View.VISIBLE
        binding.buttonReady.visibility = View.INVISIBLE
        reverses.forEach { it.visibility = View.VISIBLE }
        binding.memoScrollView.smoothScrollTo(0,0)
    }

}