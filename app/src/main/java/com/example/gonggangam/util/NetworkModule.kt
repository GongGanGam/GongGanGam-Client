package com.example.gonggangam.util

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://gonggangam.site/"

private val loggingIntercepter = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val okHttpBuilder = OkHttpClient.Builder()
    .addInterceptor(AppInterceptor())
    .addNetworkInterceptor(loggingIntercepter)
    .connectTimeout(5, TimeUnit.SECONDS)
    .readTimeout(5, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.SECONDS)

fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain){
        val request = request().newBuilder()
            .addHeader("x-access-token", PrefManager.jwt)
            .build()
        proceed(request)
    }
}
