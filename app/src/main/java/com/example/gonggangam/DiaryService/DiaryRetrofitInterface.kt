package com.example.gonggangam.DiaryService

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DiaryRetrofitInterface {

    // 세영님쪽 api
//    @Multipart
//    @POST("app/diarys")
//    fun diaryWrite(
//        @Header("x-access-token") jwt: String,
//        @Part uploadImg: MultipartBody.Part,
//        @PartMap data: HashMap<String, RequestBody>,
//    ): Call<WriteResult>
    @POST("app/diarys")
    fun diaryWrite(
        @Header("x-access-token") jwt: String,
        @Body body: WriteDiary,
    ): Call<BasicResponse>
    @GET("app/diarys")
    fun getCalendar(
        @Header("x-access-token") jwt: String,
        @Query("year") year: Int,
        @Query("month") month : Int ): Call<DayResponse>


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

    @GET("app/diarys/share/{diaryIdx}")
    fun receivedDiary(
        @Header("x-access-token") jwt:String,
        @Path ("diaryIdx") diaryIdx: Int,
    ): Call<ReceivedDiaryResponse>

    @GET("app/diarys/answer/{answerIdx}")
    fun receivedAnswer(
        @Header("x-access-token") jwt:String,
        @Path ("answerIdx") answerIdx: Int,
    ): Call<ReceivedAnswerResponse>

    @PATCH("app/diarys/answer/{answerIdx}")
    fun rejectAnswer(
        @Header("x-access-token") jwt:String,
        @Path ("answerIdx") answerIdx: Int,
    ): Call<BasicResponse>

}
