package com.example.owocewarzywa.practice.quiz

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owocewarzywa.utils.DataApi
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _apiStatus = MutableLiveData<String>()
    val apiStatus: LiveData<String> = _apiStatus

    private val _apiResponse = MutableLiveData<List<QuizData>>()
    val apiResponse: LiveData<List<QuizData>> = _apiResponse

    private val _question1 = MutableLiveData<String>()
    val question1: LiveData<String> = _question1

    private val _q1answer1 = MutableLiveData<String>()
    val q1answer1: LiveData<String> = _q1answer1

    private val _q1answer2 = MutableLiveData<String>()
    val q1answer2: LiveData<String> = _q1answer2

    private val _q1answer3 = MutableLiveData<String>()
    val q1answer3: LiveData<String> = _q1answer3

    private val _question2 = MutableLiveData<String>()
    val question2: LiveData<String> = _question2

    private val _q2answer1 = MutableLiveData<String>()
    val q2answer1: LiveData<String> = _q2answer1

    private val _q2answer2 = MutableLiveData<String>()
    val q2answer2: LiveData<String> = _q2answer2

    private val _q2answer3 = MutableLiveData<String>()
    val q2answer3: LiveData<String> = _q2answer3

    private val _question3 = MutableLiveData<String>()
    val question3: LiveData<String> = _question3

    private val _q3answer1 = MutableLiveData<String>()
    val q3answer1: LiveData<String> = _q3answer1

    private val _q3answer2 = MutableLiveData<String>()
    val q3answer2: LiveData<String> = _q3answer2

    private val _q3answer3 = MutableLiveData<String>()
    val q3answer3: LiveData<String> = _q3answer3


    init {
        resetData()
    }

    fun resetData() {
        _question1.value = ""
        _q1answer1.value = ""
        _q1answer2.value = ""
        _q1answer3.value = ""
        _question2.value = ""
        _q2answer1.value = ""
        _q2answer2.value = ""
        _q2answer3.value = ""
        _question3.value = ""
        _q3answer1.value = ""
        _q3answer2.value = ""
        _q3answer3.value = ""

    }

    fun getQuizData() {
        _question1.value = "h"
        _q1answer1.value = "h"
        _q1answer2.value = "h"
        _q1answer3.value = "h"
        _question2.value = "h"
        _q2answer1.value = "h"
        _q2answer2.value = "h"
        _q2answer3.value = "h"
        _question3.value = "h"
        _q3answer1.value = "h"
        _q3answer2.value = "h"
        _q3answer3.value = "h"
    }

    private fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = DataApi.retrofitService.getQuiz()
                _apiResponse.value = listResult
                _apiStatus.value = "Success"
            } catch (e: Exception) {
                _apiStatus.value = "Error"
            }
        }
    }
}
