package com.example.gonggangam

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://3.34.199.201:3000"
var gson = GsonBuilder().setLenient().create()
fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit
}

fun getKakaoLoginRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://kauth.kakao.com/oauth/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit
}
