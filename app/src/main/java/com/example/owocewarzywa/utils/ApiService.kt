package com.example.owocewarzywa.utils

import com.example.owocewarzywa.practice.fill.FillData
import com.example.owocewarzywa.practice.flashcards.FlashcardData
import com.example.owocewarzywa.practice.quiz.QuizData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://jezykowy-zawrot-glowy.herokuapp.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//factory dla JSONa
private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface DataApiService {
    @GET("users")
    suspend fun getQuiz(
        @Query("task") task: String,
        @Query("level") level:String,
        @Query("language") language: String,
        @Query("category") category: String
    ): List<QuizData>

    @GET("users")
    suspend fun getFill(
        @Query("task") task: String,
        @Query("level") level:String,
        @Query("language") language: String,
        @Query("category") category: String
    ): List<FillData>

    @GET("users")
    suspend fun getFlashcards(
        @Query("task") task: String,
        @Query("level") level:String,
        @Query("language") language: String,
        @Query("category") category: String
    ): List<FlashcardData>
}

object DataApi {
    val retrofitService : DataApiService by lazy {
        retrofit.create(DataApiService::class.java) }
}