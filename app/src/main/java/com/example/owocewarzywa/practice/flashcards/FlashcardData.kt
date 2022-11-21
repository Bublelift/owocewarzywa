package com.example.owocewarzywa.practice.flashcards

import com.squareup.moshi.Json

data class FlashcardData(
    val image: String,
    val hint: String,
    @Json(name = "name") val quest: String,
    @Json(name = "answer") val answer: String
)
