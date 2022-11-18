package com.example.owocewarzywa.practice.quiz

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.owocewarzywa.utils.DataApi
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _apiStatus = MutableLiveData<String>()

    private val _apiResponse = MutableLiveData<List<QuizData>>()

    private val _question1 = MutableLiveData<String>()
    val question1: LiveData<String> = _question1

    private val _question2 = MutableLiveData<String>()
    val question2: LiveData<String> = _question2

    private val _question3 = MutableLiveData<String>()
    val question3: LiveData<String> = _question3

    private var _questions = MutableLiveData<List<String>>()
    val questions: LiveData<List<String>> = _questions

    private var _correctAnswers = MutableLiveData<List<String>>()
    val correctAnswers: LiveData<List<String>> = _correctAnswers

    private var _q1answers = MutableLiveData<List<String>>()
    val q1answers: LiveData<List<String>> = _q1answers

    private var _q2answers = MutableLiveData<List<String>>()
    val q2answers: LiveData<List<String>> = _q2answers

    private var _q3answers = MutableLiveData<List<String>>()
    val q3answers: LiveData<List<String>> = _q3answers

    private fun prepareQuizData() {
        if (_apiResponse.value != null) {
            var q1 = ""
            var q2 = ""
            var q3 = ""
            var a1 = ""
            var a2 = ""
            var a3 = ""
            with(_apiResponse.value!!.first()) {
                a1 = this.correctAnswer
                q1 = this.question
                _q1answers.value = listOf(this.correctAnswer, this.answer2, this.answer3).shuffled()
            }
            with(_apiResponse.value!!.get(1)) {
                q2 = this.question
                a2 = this.correctAnswer
                _q2answers.value = listOf(this.correctAnswer, this.answer2, this.answer3).shuffled()
            }
            with(_apiResponse.value!!.last()) {
                q3 = this.question
                a3 = this.correctAnswer
                _q3answers.value = listOf(this.correctAnswer, this.answer2, this.answer3).shuffled()
            }
            _questions.value = listOf(q1, q2, q3)
            _correctAnswers.value = listOf(a1, a2, a3)
        }
        else {
            _questions.value = listOf("loading data", "loading data", "loading data")
            _q1answers.value = listOf("loading data", "loading data", "loading data")
            _q2answers.value = listOf("loading data", "loading data", "loading data")
            _q3answers.value = listOf("loading data", "loading data", "loading data")
        }
    }

    suspend fun initQuiz() {
        try {
            val listResult = DataApi.retrofitService.getQuiz("quiz", "easy", "english", "nature")
            Log.e("Result:", listResult.toString())
            _apiResponse.value = listResult
            _apiStatus.value = "Success"
            prepareQuizData()
        } catch (e: Exception) {
            _apiStatus.value = "Error"
        }
    }


    //    init {
//        resetData()
//    }
//
//    fun resetData() {
//        _question1.value = ""
//        _q1answer1.value = ""
//        _q1answer2.value = ""
//        _q1answer3.value = ""
//        _question2.value = ""
//        _q2answer1.value = ""
//        _q2answer2.value = ""
//        _q2answer3.value = ""
//        _question3.value = ""
//        _q3answer1.value = ""
//        _q3answer2.value = ""
//        _q3answer3.value = ""
//
//    }
}
