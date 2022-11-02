package com.example.owocewarzywa.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.owocewarzywa.R
import com.example.owocewarzywa.chat.MessageItemUi.Companion.TYPE_FRIEND_MESSAGE
import com.example.owocewarzywa.chat.MessageItemUi.Companion.TYPE_MY_MESSAGE

class ChatAdapter(var data: MutableList<MessageItemUi>) : RecyclerView.Adapter<MessageViewHolder<*>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_MY_MESSAGE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.chat_message, parent, false)
                MyMessageViewHolder(view)
            }
            TYPE_FRIEND_MESSAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.other_chat_message, parent, false)
                FriendMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
    //holder: BaseViewHolder<*>
    override fun onBindViewHolder(holder: MessageViewHolder<*>, position: Int) {
        val item = data[position]
        Log.d("adapter View", position.toString() + item.content)
        when (holder) {
            is MyMessageViewHolder -> holder.bind(item)
            is FriendMessageViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].messageType

    class MyMessageViewHolder(val view: View) : MessageViewHolder<MessageItemUi>(view) {
        private val messageContent = view.findViewById<TextView>(R.id.message)

        override fun bind(item: MessageItemUi) {
            messageContent.text = item.content
//            messageContent.textColor = item.textColor
        }
    }
    class FriendMessageViewHolder(val view: View) : MessageViewHolder<MessageItemUi>(view) {
        private val messageContent = view.findViewById<TextView>(R.id.message)

        override fun bind(item: MessageItemUi) {
            messageContent.text = item.content
//            messageContent.textColor= item.textColor
        }


    }
}