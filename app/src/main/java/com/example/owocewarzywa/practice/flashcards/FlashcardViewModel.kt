package com.example.owocewarzywa.practice.flashcards

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.owocewarzywa.utils.DataApi

class FlashcardViewModel: ViewModel() {

    private val _apiStatus = MutableLiveData<String>()
    private val _apiResponse = MutableLiveData<List<FlashcardData>>()

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    val hints = MutableLiveData(2)
    val currentHint = MutableLiveData<String>()
    val currentImage = MutableLiveData<String>()

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private val _currentFlashWord = MutableLiveData<String>()
    val currentFlashWord: LiveData<String>
        get() = _currentFlashWord

    // List of words used in the game
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private var allWordsList = listOf(
        FlashcardData("https://www.leclerc.rzeszow.pl/foto_shop/184/5907471415485_T2.jpg",
            "zwierzemta",
            "Fiszka cox",
            "Fiszka koks"),
        FlashcardData("https://royalsub.pl/wp-content/uploads/2015/09/SW-DE-COO-S-OatmRaisin_1002.png",
            "zwierzemta2",
            "Fiszka cox2",
            "Fiszka koks2"),
        FlashcardData("https://www.leclerc.rzeszow.pl/foto_shop/184/5907471415485_T2.jpg",
            "szamba24",
            "Fiszka cox3",
            "Fiszka koks3"),
    )

//    init {
//        getNextWord()
//    }




    private fun getNextWord() {

        _currentFlashWord.value = _apiResponse.value!![_currentWordCount.value!!].quest
        currentWord = _apiResponse.value!![_currentWordCount.value!!].answer
        currentHint.value = _apiResponse.value!![_currentWordCount.value!!].hint
        currentImage.value = _apiResponse.value!![_currentWordCount.value!!].image
        _currentWordCount.value = _currentWordCount.value?.inc()
    }


    fun reinitializeData() {
        _score.value = 0
        hints.value = 2
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    //EWENTUALNIE DO USUNIĘCIA
    fun destroy() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
    }


    /*
    * Increases the game score if the player's word is correct.
    */
    private fun increaseScore() {
        when (hints.value) {
            2 -> _score.value = _score.value?.plus(25)
            1 -> _score.value = _score.value?.plus(15)
            else -> _score.value = _score.value?.plus(5)
        }

    }

    /*
    * Returns true if the player word is correct.
    * Increases the score accordingly.
    */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS
    */
    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < 10) {
            getNextWord()
            true
        } else false
    }


    suspend fun getFlashcardsData(level: String, language: String, category: String) {
        try {
            val listResult = DataApi.retrofitService.getFlashcards("flashcards", level, language, category)
            Log.e("Result:", listResult.toString())
            _apiResponse.value = listResult
            _apiStatus.value = "Success"
            //allWordsList = listResult
            getNextWord()
        } catch (e: Exception) {
            _apiStatus.value = "Error"
        }
    }
}