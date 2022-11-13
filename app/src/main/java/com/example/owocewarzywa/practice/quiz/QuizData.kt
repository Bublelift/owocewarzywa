package com.example.owocewarzywa.practice.quiz

import com.squareup.moshi.Json

data class QuizData(
    val id: String, @Json(name = "img_src") val imgSrcUrlCustomName: String
)
