package com.example.owocewarzywa.recyclerview.item

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import com.example.owocewarzywa.R
import com.example.owocewarzywa.model.User
import com.example.owocewarzywa.utils.FirestoreUtil
import com.example.owocewarzywa.utils.GlideApp
import com.example.owocewarzywa.utils.StorageUtil
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*

class PersonItem(val person: User, val userId: String, private val context: Context) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.user_nickname.text = person.name
        viewHolder.user_bio.text = person.bio
        viewHolder.user_score.text = context.getString(R.string.score) + " " + (person.score?.toString() ?: "0")
        if (person.profilePicturePath != null && person.profilePicturePath != "") {
            GlideApp.with(context).load(StorageUtil.pathToReference(person.profilePicturePath))
                .placeholder(R.drawable.no_profile)
                .error(R.drawable.no_profile)
                .fallback(R.drawable.no_profile)
                .into(viewHolder.user_pic)
        } else {
            GlideApp.with(context).load(R.drawable.no_profile)
                .into(viewHolder.user_pic)
        }
//        var chatChannel: String? = null
        FirestoreUtil.getOrCreateChatChannel(userId) { channelId ->
            FirestoreUtil.getMsgReadStatus(channelId) {toReadBy ->
                if (toReadBy == FirebaseAuth.getInstance().currentUser!!.uid) {
                    viewHolder.new_msg.visibility = android.view.View.VISIBLE
                    viewHolder.user_nickname.setTextColor(Color.parseColor("#000000"))
                    viewHolder.user_bio.setTextColor(Color.parseColor("#000000"))
                    viewHolder.user_bio.setTypeface(Typeface.DEFAULT_BOLD)
                }
            }


        }
//        val toReadBy = FirestoreUtil.getMsgReadStatus(chatChannel.toString())
//        if (toReadBy == FirebaseAuth.getInstance().currentUser!!.uid)
//            viewHolder.new_msg.visibility = android.view.View.VISIBLE
    }
    override fun getLayout() = R.layout.item_person

//    override fun getExtras(): MutableMap<String, Any> {
//        var chatChannel: String? = null
//        FirestoreUtil.getOrCreateChatChannel(userId) { channelId -> chatChannel = channelId.toString() }
//        var toReadBy = FirestoreUtil.getMsgReadStatus(chatChannel.toString())
//        return mutableMapOf<String, Any>("toReadBy" to toReadBy)
//    }

}