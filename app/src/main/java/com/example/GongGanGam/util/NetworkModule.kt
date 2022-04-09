package com.example.gonggangam

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://3.36.219.12:3000/"
var gson = GsonBuilder().setLenient().create()
val clientBuilder = OkHttpClient.Builder() // more detail retrofit log
val loggingIntercepter = HttpLoggingInterceptor()

//var okHttpClient = OkHttpClient().newBuilder()
//    .connectTimeout(30, TimeUnit.SECONDS)
//    .readTimeout(30, TimeUnit.SECONDS)
//    .writeTimeout(30, TimeUnit.SECONDS)
//    .build()

fun getRetrofit(): Retrofit {
    loggingIntercepter.level = HttpLoggingInterceptor.Level.BODY
    clientBuilder.addInterceptor(loggingIntercepter)
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientBuilder.build())
        .build()

    return retrofit
}