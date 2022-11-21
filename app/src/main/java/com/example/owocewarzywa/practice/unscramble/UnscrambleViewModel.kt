package com.example.owocewarzywa.practice.unscramble

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.owocewarzywa.utils.DataApi
import kotlin.random.Random

/**
 * ViewModel containing the app data and methods to process the data
 */
class UnscrambleViewModel : ViewModel(){

    private val _apiStatus = MutableLiveData<String>()
    val apiStatus: LiveData<String> = _apiStatus

    private val _apiResponse = MutableLiveData<List<UnscrambleData>>()

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    // List of words used in the game
    //private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String


    /*
    * Updates currentWord and currentScrambledWord with the next word.
    */
    private fun getNextWord() {

        currentWord = _apiResponse.value!![_currentWordCount.value!!].name
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        _currentScrambledWord.value = String(tempWord)
        _currentWordCount.value = _currentWordCount.value?.inc()
//        var rng = Random(System.currentTimeMillis())
//        currentWord = allWordsList[rng.nextInt(0, (allWordsList.size-1))]
//        val tempWord = currentWord.toCharArray()
//        tempWord.shuffle()
//
//        while (String(tempWord).equals(currentWord, false)) {
//            tempWord.shuffle()
//        }
//        if (wordsList.contains(currentWord)) {
//            getNextWord()
//        } else {
//            _currentScrambledWord.value = String(tempWord)
//            _currentWordCount.value = _currentWordCount.value?.inc()
//            wordsList.add(currentWord)
//        }
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        //wordsList.clear()
        getNextWord()
    }

    //EWENTUALNIE DO USUNIĘCIA
    fun destroy() {
        _score.value = 0
        _currentWordCount.value = 0
        //wordsList.clear()
    }


    /*
    * Increases the game score if the player's word is correct.
    */
    private fun increaseScore() {
        _score.value = _score.value?.plus(20)
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

    suspend fun initUnscramble(language: String, level: String, category: String) {
        try {
            val listResult =
                DataApi.retrofitService.getUnscramble("unscramble", level, language, category)
            _apiResponse.value = listResult
            _apiStatus.value = "Success"
            getNextWord()
        } catch (e: Exception) {
            _apiStatus.value = "Error"
        }
    }
}