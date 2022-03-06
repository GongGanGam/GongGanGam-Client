package com.example.gonggangam.DiaryService

import com.example.gonggangam.AuthService.AuthResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DiaryRetrofitInterface {
    @GET("app/diarys/share")
    fun getDiaries(
        @Query("page") page: Int,
        @Header("x-access-token") jwt: String
    ): Call<ReceivedDiaryResponse>

    @GET("app/diarys/answer")
    fun getAnswers(
        @Query("page") page: Int,
        @Header("x-access-token") jwt: String
    ): Call<ReceivedAnswerResponse>
}
