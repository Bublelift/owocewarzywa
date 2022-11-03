package com.example.owocewarzywa.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PracticeViewModel: ViewModel(){
    private val _language = MutableLiveData<String>()
    val language: LiveData<String> = _language

    private val _topic = MutableLiveData<String>()
    val topic: LiveData<String> = _topic

    private val _difficulty = MutableLiveData<String>()
    val difficulty: LiveData<String> = _difficulty

    init {
        resetData()
    }

    fun resetData() {
        _language.value = ""
        _topic.value = ""
        _difficulty.value = ""
    }

    fun setPractice(language: String, topic: String, difficulty: String) {
        _language.value = language
        _topic.value = topic
        _difficulty.value = difficulty
    }
}