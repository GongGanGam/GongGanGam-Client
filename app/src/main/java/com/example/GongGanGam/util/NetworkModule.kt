package com.example.GongGanGam.util

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://gonggangam.site/"
//private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
//    level = HttpLoggingInterceptor.Level.BODY
//}

var gson = GsonBuilder().setLenient().create()
private val clientBuilder = OkHttpClient.Builder() // more detail retrofit log
private val loggingIntercepter = HttpLoggingInterceptor()

//private val okHttpClient = clientBuilder
//    .connectTimeout(1, TimeUnit.SECONDS)
//    .readTimeout(1, TimeUnit.SECONDS)
//    .writeTimeout(1, TimeUnit.SECONDS)
//    .build()

fun getRetrofit(): Retrofit {
    loggingIntercepter.level = HttpLoggingInterceptor.Level.BODY
    clientBuilder.addInterceptor(loggingIntercepter)

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientBuilder.build())
        .build()
}
