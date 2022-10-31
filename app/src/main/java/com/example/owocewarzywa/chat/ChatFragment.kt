package com.example.owocewarzywa.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.owocewarzywa.MainActivity
import com.example.owocewarzywa.databinding.FragmentChatBinding
import com.example.owocewarzywa.model.ChatViewModel
import com.example.owocewarzywa.utils.FirestoreUtil
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.kotlinandroidextensions.Item

class ChatFragment : Fragment() {

    private var binding: FragmentChatBinding? = null

    private val chatViewModel: ChatViewModel by activityViewModels()

    private lateinit var messagesListenerRegistration: ListenerRegistration


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentBinding = FragmentChatBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        (activity as MainActivity).supportActionBar?.title = chatViewModel.chatUserName.value.toString()
        val otherUserId = chatViewModel.chatUserId.toString()
        FirestoreUtil.getOrCreateChatChannel(otherUserId) { channelId ->
            messagesListenerRegistration = FirestoreUtil.addChatMessagesListener(channelId, requireContext(), this::onMessagesChanged)
        }

        return fragmentBinding.root

    }

    override fun onDestroyView() {
        chatViewModel.resetData()
        super.onDestroyView()
        binding = null
    }

    private fun onMessagesChanged(messages: List<Item>) {
        Toast.makeText(requireContext(), "onMessagesChangedRunning", Toast.LENGTH_LONG).show()
    }



}