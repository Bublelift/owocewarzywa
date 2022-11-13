package com.example.owocewarzywa.utils

import com.example.owocewarzywa.practice.quiz.QuizData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

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
    @GET("photos")
    suspend fun getQuiz(): List<QuizData>
//    suspend fun getPhotos(): String
}

object DataApi {
    val retrofitService : DataApiService by lazy {
        retrofit.create(DataApiService::class.java) }
}