package com.example.owocewarzywa.chat

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.owocewarzywa.MainActivity
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentChatBinding
import com.example.owocewarzywa.model.ChatViewModel
import com.example.owocewarzywa.model.TextMessage
import com.example.owocewarzywa.utils.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.chat_message.*
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*


class ChatFragment : Fragment() {

    private var binding: FragmentChatBinding? = null

    private val chatViewModel: ChatViewModel by activityViewModels()

    private lateinit var messagesListenerRegistration: ListenerRegistration

    private var shouldInitRecyclerView = true

    private lateinit var  messagesSection: Section

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        val fragmentBinding = FragmentChatBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        (activity as MainActivity).supportActionBar?.title = chatViewModel.chatUserName.value.toString()
        val otherUserId = chatViewModel.chatUserId.value.toString()
        FirestoreUtil.getOrCreateChatChannel(otherUserId) { channelId ->
            FirestoreUtil.setMsgReadStatus(channelId, otherUserId, "")
            messagesListenerRegistration = FirestoreUtil.addChatMessagesListener(channelId, requireContext(), this::onMessagesChanged)
            chat_send.setOnClickListener{
                if (chat_input.text!!.isNotBlank()) {
                    val messageToSend = TextMessage(
                        chat_input.text.toString(),
                        Calendar.getInstance().time,
                        FirebaseAuth.getInstance().currentUser!!.uid)
                    chat_input.setText("")
                    FirestoreUtil.sendMessage(messageToSend, channelId)
                    FirestoreUtil.setMsgReadStatus(channelId, otherUserId, otherUserId)
                }
            }
        }

        return fragmentBinding.root

    }

    private var itemToShow: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        itemToShow = menu.findItem(R.id.action_copy_id)
        menu.findItem(R.id.action_copy_id).isVisible = true
    }


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.bar, menu)
//    }

    override fun onDestroyView() {
        itemToShow!!.isVisible = false
        chatViewModel.resetData()
        shouldInitRecyclerView = true
        super.onDestroyView()
        binding = null
    }

    private fun onMessagesChanged(messages: List<Item>) {
        fun init() {
            recycler_view_messages.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = GroupAdapter<ViewHolder>().apply {
                    messagesSection = Section(messages)
                    this.add(messagesSection)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = messagesSection.update(messages)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

        //if (recycler_view_messages. != null)
            recycler_view_messages.scrollToPosition(recycler_view_messages.adapter!!.itemCount - 1)
    }

}