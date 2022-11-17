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

//    private var _q1answers: MutableList<MutableLiveData<String>> = mutableListOf()
    private var _q1answers = MutableLiveData<List<String>>()
    val q1answers: LiveData<List<String>> = _q1answers

    private var _q2answers = MutableLiveData<List<String>>()
    val q2answers: LiveData<List<String>> = _q2answers

    private var _q3answers = MutableLiveData<List<String>>()
    val q3answers: LiveData<List<String>> = _q3answers

    private fun prepareQuizData() {
        if (_apiResponse.value != null) {
            with(_apiResponse.value!!.first()) {
                _question1.value = this.question
                _q1answers.value = listOf(this.correctAnswer, this.answer2, this.answer3).shuffled()
            }
            with(_apiResponse.value!!.get(1)) {
                _question2.value = this.question
                _q2answers.value = listOf(this.correctAnswer, this.answer2, this.answer3).shuffled()
            }
            with(_apiResponse.value!!.last()) {
                _question3.value = this.question
                _q3answers.value = listOf(this.correctAnswer, this.answer2, this.answer3).shuffled()
            }
//            _question1.value = _apiResponse.value!!.first().question
//            _apiResponse.value!!.first().correctAnswer
//            _q1answers = answers1
//            _q1answer1.value = _
//            _q1answer2.value = ""
//            _q1answer3.value = ""
//            _question2.value = ""
//            _q2answer1.value = ""
//            _q2answer2.value = ""
//            _q2answer3.value = ""
//            _question3.value = ""
//            _q3answer1.value = ""
//            _q3answer2.value = ""
//            _q3answer3.value = ""
        }
        else {
            _question1.value = "loading data"
            _q1answers.value = listOf("loading data", "loading data", "loading data")
            _question2.value = "loading data"
            _q2answers.value = listOf("loading data", "loading data", "loading data")
            _question3.value = "loading data"
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
