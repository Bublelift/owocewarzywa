package com.example.owocewarzywa.practice.flashcards

import com.squareup.moshi.Json

data class FlashcardData(
    val image: String,
    val category: String,
    @Json(name = "TODOenglish") val quest: String,
    @Json(name = "TODOpolish") val answer: String
)
