package com.example.gonggangam.DiaryService

import com.example.gonggangam.AuthService.AuthResponse
import retrofit2.Call
import retrofit2.http.*

interface DiaryRetrofitInterface {
    @GET("app/diarys/share")
    fun getDiaries(
        @Query("page") page: Int,
        @Header("x-access-token") jwt: String
    ): Call<ReceivedDiarysResponse>

    @GET("app/diarys/answer")
    fun getAnswers(
        @Query("page") page: Int,
        @Header("x-access-token") jwt: String
    ): Call<ReceivedAnswersResponse>

    @POST("app/diarys/answer")
    fun sendReply(
        @Header("x-access-token") jwt: String,
        @Body reply: Reply
    ): Call<BasicResponse>

    @GET("app/diarys/answer/{answerIdx}")
    fun getAnswer(
        @Header("x-access-token") jwt: String,
        @Path ("answerIdx") answerIdx: Int,
    ): Call<ReceivedAnswerResponse>

    @GET("app/diarys/share/{diaryIdx}")
    fun receivedDiary(
        @Header("x-access-token") jwt:String,
        @Path ("diaryIdx") diaryIdx: Int,
    ): Call<ReceivedDiaryResponse>

    @PATCH("app/diarys/answer/{answerIdx}")
    fun receivedAnswer(
        @Header("x-access-token") jwt:String,
        @Path ("answerIdx") answerIdx: Int,
    ): Call<BasicResponse>
}
