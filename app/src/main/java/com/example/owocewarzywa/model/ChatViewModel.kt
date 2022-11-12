package com.example.owocewarzywa.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val _chatUserName = MutableLiveData<String>()
    val chatUserName: LiveData<String> = _chatUserName

    private val _chatUserId = MutableLiveData<String>()
    val chatUserId: LiveData<String> = _chatUserId

    private val _search = MutableLiveData<String>()
    val search: LiveData<String> = _search

    init {
        resetData()
    }

    fun resetData() {
        _chatUserName.value = ""
        _chatUserId.value = ""
    }

    fun setChatUser(username: String, uid: String) {
        _chatUserName.value = username
        _chatUserId.value = uid
    }

    fun search(input: String) {
        _search.value = input
    }
}