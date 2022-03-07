package com.example.gonggangam.DiaryService

import com.example.gonggangam.AuthService.AuthResponse
import retrofit2.Call
import retrofit2.http.*

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

    @POST("app/diarys/answer")
    fun sendReply(
        @Header("x-access-token") jwt: String,
        @Body reply: Reply
    ): Call<Response>
}
