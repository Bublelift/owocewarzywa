package com.example.owocewarzywa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.databinding.FragmentFeedbackBinding
import com.example.owocewarzywa.model.ChatViewModel
import com.example.owocewarzywa.utils.FirestoreUtil
import java.util.*
import kotlin.concurrent.schedule

class FeedbackFragment : Fragment() {

    private lateinit var binding: FragmentFeedbackBinding
    private val chatViewModel: ChatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener{
            sendFeedback()
        }
        if (chatViewModel.chatUserId.value != null) {
            binding.feedback.setText("Zgłoszenie użytkownika " +
                    "${chatViewModel.chatUserName.value} (${chatViewModel.chatUserId.value}):\n")
        }
    }

    private fun sendFeedback() {
        val content = binding.feedback.text.toString()
        FirestoreUtil.sendFeedback(content)
        chatViewModel.resetData()
        Toast.makeText(requireContext(), "Przyjęliśmy twoje zgłoszenie!", Toast.LENGTH_LONG).show()
        Timer("Navigate up", false).schedule(1750) {
            requireActivity().runOnUiThread {
                findNavController().navigateUp()
            }
        }
    }

}