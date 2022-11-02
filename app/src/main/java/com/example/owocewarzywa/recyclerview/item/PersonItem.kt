package com.example.owocewarzywa.recyclerview.item

import android.content.Context
import com.example.owocewarzywa.R
import com.example.owocewarzywa.model.User
import com.example.owocewarzywa.utils.GlideApp
import com.example.owocewarzywa.utils.StorageUtil
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*

class PersonItem(val person: User, val userId: String, private val context: Context) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.user_nickname.text = person.name
        viewHolder.user_bio.text = person.bio
        if (person.profilePicturePath != null && person.profilePicturePath != "")
            GlideApp.with(context).load(StorageUtil.pathToReference(person.profilePicturePath))
                .placeholder(R.drawable.no_profile)
                .into(viewHolder.user_pic)
    }
    override fun getLayout() = R.layout.item_person

}