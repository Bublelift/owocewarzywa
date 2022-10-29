//package com.example.owocewarzywa.chat
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.lifecycle.Observer
//
//class ChatFragment : Fragment() {
//
//    private lateinit var viewModel: ChatViewModel
//
//    private var chatAdapter = ChatAdapter(mutableListOf())
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        viewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
//        chatAdapter.data=viewModel.messages
//        subscribeOnAddMessage()
//    }
//
//    private fun subscribeOnAddMessage() {
//        viewModel.notifyNewMessageInsertedLiveData.observe(viewLifecycleOwner, Observer {
//            chatAdapter.notifyItemInserted(it)
//        })
//    }
//
//
////    override fun onCreateView(
////        inflater: LayoutInflater,
////        container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View? {
////        super.onCreateView(inflater, container, savedInstanceState)
////        bot_conversation.apply {
////            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
////                .apply {
////                    stackFromEnd = true
////                    isSmoothScrollbarEnabled = true
////                }
////            adapter = chatAdapter
////        }
////
////        input_layout.onSendClicked = {
////            viewModel.sendMessage(it)
////            hideKeyboard(activity)
////        }
////    }
//}