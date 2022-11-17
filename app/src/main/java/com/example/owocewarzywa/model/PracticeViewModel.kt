package com.example.owocewarzywa.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PracticeViewModel: ViewModel(){
    private val _type = MutableLiveData<String>()
    val type: LiveData<String> = _type

    private val _language = MutableLiveData<String>()
    val language: LiveData<String> = _language

    private val _topic = MutableLiveData<String>()
    val topic: LiveData<String> = _topic

    private val _difficulty = MutableLiveData<String>()
    val difficulty: LiveData<String> = _difficulty

    init {
        resetAll()
    }

    fun resetAll() {
        _language.value = ""
        _topic.value = ""
        _difficulty.value = ""
        _type.value = ""
    }

    fun setPracticeType(type: String) {
        _type.value = type
    }

    fun setPracticeSettings(language: String, topic: String, difficulty: String) {
        _language.value = when (language) {
            in listOf("Polski", "Polish") -> "polish"
            in listOf("Niemiecki", "German") -> "german"
            else -> "english"
        }
        _topic.value = when (topic) {
            in listOf("Natura", "Nature")-> "nature"
            in listOf("Styl życia", "Lifestyle") -> "lifestyle"
            else -> "technology"
        }
        _difficulty.value = when (difficulty) {
            in listOf("Łatwy", "Easy") -> "easy"
            in listOf("Trudny", "Hard") -> "hard"
            else -> "hard"
        }
        Log.e("practice", "lan: $language, top: $topic, dif: $difficulty")
    }
}