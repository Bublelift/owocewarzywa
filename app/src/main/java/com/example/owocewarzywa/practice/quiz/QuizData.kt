package com.example.owocewarzywa.practice.quiz

import com.squareup.moshi.Json

data class QuizData(
    val question: String,
    val correctAnswer: String,
    @Json(name = "incorrectAnswer1") val answer2: String,
    @Json(name = "incorrectAnswer2") val answer3: String
)
