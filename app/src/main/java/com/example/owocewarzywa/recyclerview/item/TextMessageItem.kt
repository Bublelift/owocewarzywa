package com.example.owocewarzywa.recyclerview.item

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.owocewarzywa.R
import com.example.owocewarzywa.model.TextMessage
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.chat_message.*
import java.text.SimpleDateFormat
import java.util.*

class TextMessageItem(val message: TextMessage, val context: Context) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.message_text.text = message.text
        setTimeText(viewHolder)
        setMessageRootGravity(viewHolder)
    }

    private fun setTimeText(viewHolder: ViewHolder) {
        val dateFormat = SimpleDateFormat("HH:mm, dd.MMMM")
            //.getDateInstance(SimpleDateFormat., Locale.forLanguageTag("PL"))
        viewHolder.message_time.text = dateFormat.format(message.time)
    }

    private fun setMessageRootGravity(viewHolder: ViewHolder) {
        if (message.senderId == FirebaseAuth.getInstance().currentUser?.uid) {
            viewHolder.message_root.apply {
                setBackgroundResource(R.drawable.my_chat_bubble)
                val layoutParameters = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.END)
                this.layoutParams = layoutParameters
            }
            viewHolder.message_text.apply{
                setTextColor(resources.getColor(R.color.white))
            }
            viewHolder.message_time.apply{
                setTextColor(resources.getColor(R.color.grey_400))
            }
        }
        else {
            viewHolder.message_root.apply {
                setBackgroundResource(R.drawable.other_chat_bubble)
                val layoutParameters = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.START)
                this.layoutParams = layoutParameters
            }
            viewHolder.message_text.apply{
                setTextColor(resources.getColor(R.color.black))
            }
            viewHolder.message_time.apply{
                setTextColor(resources.getColor(R.color.grey_700))
            }
        }
    }

    override fun getLayout() = R.layout.chat_message

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other !is TextMessageItem)
            return false
        if (this.message != other.message)
            return false
        return true
    }

    override fun equals(other: Any?): Boolean {
        return isSameAs(other as? TextMessageItem)
    }
}