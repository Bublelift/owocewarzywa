package com.example.owocewarzywa.recyclerview.item

import android.content.Context
import com.example.owocewarzywa.R
import com.example.owocewarzywa.model.TextMessage
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class TextMessageItem(val message: TextMessage, val context: Context) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getLayout() = R.layout.my_chat_message
}