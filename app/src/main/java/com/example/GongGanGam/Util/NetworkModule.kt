package com.example.gonggangam

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://3.36.219.12:3000/"
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

fun getNaverLoginRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://nid.naver.com/oauth2.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit
}
