package com.example.owocewarzywa.practice.fill

import android.R
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owocewarzywa.utils.DataApi
import kotlinx.coroutines.launch
import kotlin.random.Random

class FillViewModel : ViewModel() {

    private val _apiStatus = MutableLiveData<String>()
    val apiStatus: LiveData<String> = _apiStatus

    private val _apiResponse = MutableLiveData<List<FillData>>()
    val apiResponse: LiveData<List<FillData>> = _apiResponse

    private val _fillText = MutableLiveData<String>()
    val fillText: LiveData<String> = _fillText

    private val _word1 = MutableLiveData<String>()
    val word1: LiveData<String> = _word1

    private val _word2 = MutableLiveData<String>()
    val word2: LiveData<String> = _word2

    private val _word3 = MutableLiveData<String>()
    val word3: LiveData<String> = _word3

    private val _answerList = MutableLiveData<ArrayList<String>>()
    val answerList: LiveData<ArrayList<String>> = _answerList


    private fun prepareText() {
        val text: String
        text = if (apiResponse.value == null) {
            "Loading data. Please wait."
        } else {
            apiResponse.value!!.first().text
        }
        var words = text.split(" ").toMutableList()
        var rng = Random(System.currentTimeMillis())
        var index1 = rng.nextInt(0, (words.size - 1))
        var index2: Int
        do {
            index2 = rng.nextInt(0, (words.size - 1))
        } while (index2 == index1 || words[index1] == words[index2])
        var index3: Int
        do {
            index3 = rng.nextInt(0, (words.size - 1))
        } while (index3 == index1 || index3 == index2 || words[index1] == words[index3] || words[index2] == words[index3])
        var indexes = mutableListOf(index1, index2, index3).sorted()
        _word1.value = words.elementAt(indexes.elementAt(0))
        words.set(indexes.elementAt(0), "(1)______")
        _word2.value = words.elementAt(indexes.elementAt(1))
        words.set(indexes.elementAt(1), "(2)______")
        _word3.value = words.elementAt(indexes.elementAt(2))
        words.set(indexes.elementAt(2), "(3)______")
        _fillText.value = words.joinToString(" ")

    }

    private fun prepareSpinnerList() {
        var answers = ArrayList<String>()
        answers.apply {
            add(_word1.value.toString())
            add(_word2.value.toString())
            add(_word3.value.toString())
            shuffle()
            add(0, "")
        }
        _answerList.value = answers
    }

    suspend fun initFill(language: String, level: String, category: String) {
        try {
            val listResult =
                DataApi.retrofitService.getFill("fill-in-blanks", level, language, category)
            _apiResponse.value = listResult
            _apiStatus.value = "Success"
            prepareText()
            prepareSpinnerList()
        } catch (e: Exception) {
            _apiStatus.value = "Error"
        }
    }
}