package com.example.owocewarzywa.practice.memo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.owocewarzywa.utils.DataApi

class MemoViewModel : ViewModel() {

    private val _apiStatus = MutableLiveData<String>()
    val apiStatus: LiveData<String> = _apiStatus

    private val _apiResponse = MutableLiveData<List<MemoData>>()
    val apiResponse: LiveData<List<MemoData>> = _apiResponse

    private val _currentGoal = MutableLiveData<String>()
    val currentGoal: LiveData<String> = _currentGoal

    private val _wordlist = MutableLiveData<List<String>>()
    //val wordlist: LiveData<List<String>> = _wordlist

    private val _currentIdx = MutableLiveData(-1)
    //val word2: LiveData<String> = _word2

    private val _currentTries= MutableLiveData(0)
//    val word3: LiveData<String> = _word3

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _cardsList = MutableLiveData<List<MemoData>>()
    val cardsList: LiveData<List<MemoData>> = _cardsList

//    private val _cardsList = MutableLiveData<Map<String, String>>()
//    val cardsList: LiveData<Map<String, String>> = _cardsList
    private val _backupCardsList: List<MemoData> = listOf(
        MemoData("https://thumbs.dreamstime.com/b/bochenek-bia%C5%82y-chleb-34021408.jpg", "chleb"),
        MemoData("https://www.opengift.pl/plik/6bed7bddcca639affe4a3201068ff1be9e61a576/pilka-nozna-bialyczarny-full.jpg", "piłka"),
        MemoData("https://dlabiura24.pl/woda-zywiec-zdroj-niegazowana-05l-12-szt,ggadg,a,a.jpg", "woda"),
        MemoData("https://www.gpas-cache.ford.com/guid/14e53bac-71cb-3b62-98b8-20b79ebf08f6.png", "samochód"),
        MemoData("https://atrium-targowek.pl/assets/uploads/2020/09/311595514432599157.png", "kfc"),
        MemoData("https://stalowezdrowie.pl/wp-content/uploads/2019/01/pomara%C5%84cze.jpg", "pomarańcza"),
        MemoData("https://www.creativehobby.pl/data/gfx/pictures/medium/4/2/35224_2.jpg", "kotwica"),
        MemoData("https://img.fruugo.com/product/3/99/202843993_max.jpg", "piwo"),
        MemoData("https://www.perfect-fit.pl/content/img/article/dog/all.png", "pies"),
        MemoData("https://odkrywcyplanet.pl/wp-content/uploads/2015/10/Ksiezyc-w-pelni-fotografia-styczen-2009.jpg", "księżyc")
    )

    private fun prepareCards() {
        _cardsList.value = when(_apiStatus.value) {
            "Success" -> _apiResponse.value!!.shuffled()
            else -> _backupCardsList.shuffled()
        }
        Log.e("cardlisthhhhh", _cardsList.value.toString())
        var templist = mutableListOf<String>()
        for (card in _cardsList.value!!) {
            templist.add(card.name)
        }
        Log.e("bfshufl", templist.toString())
        templist = templist.shuffled() as MutableList<String>
        Log.e("afshufl", templist.toString())
        _wordlist.value = templist.shuffled()
        Log.e("afshufl2", _wordlist.value.toString())
        tryGetNextGoal()
    }

    fun tryGetNextGoal(): Boolean {
        _currentIdx.value = _currentIdx.value!! + 1
        if (_currentIdx.value.toString() == _cardsList.value!!.size.toString()) {
            Log.e("DZIAŁAJ KURWO DZIAŁAJ", "NO KURWA DZIAŁA W KOŃCU")
            return false
        } else {
            Log.e("Zjebane gówno 1", _currentIdx.value.toString())
            Log.e("Zjebane gówno 2", _cardsList.value!!.size.toString())
            Log.e("Zjebane Gówno w chuj", (_currentIdx.value.toString() == _cardsList.value!!.size.toString()).toString())
            _currentGoal.value = _wordlist.value!![_currentIdx.value!!]
            _currentTries.value = 0
            return true
        }
    }

    fun tryGuess() {
        _currentTries.value = _currentTries.value!! + 1
    }

    fun addScore() {
        when (_currentTries.value!!) {
            1 -> _score.value = _score.value!! + 25
            2 -> _score.value = _score.value!! + 20
            3 -> _score.value = _score.value!! + 15
            4 -> _score.value = _score.value!! + 10
            else -> _score.value = _score.value!! + 5
        }
        _currentTries.value = 0
    }

    suspend fun initMemo(language: String, level: String, category: String) {
        try {
            val listResult =
                DataApi.retrofitService.getMemo("memorygame", level, language, category)
            _apiResponse.value = listResult
//            _apiStatus.value = "Success"
            _apiStatus.value = "TestDev"

//            prepareSpinnerList()
        } catch (e: Exception) {
            _apiStatus.value = "Error"
        }
        prepareCards()
    }
}