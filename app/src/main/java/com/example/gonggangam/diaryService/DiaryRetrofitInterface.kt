package com.example.gonggangam.diaryService

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DiaryRetrofitInterface {

    // 세영님쪽 api

    @Multipart
    @POST("app/diarys")
    fun writeDiary(
        @Part uploadImg: MultipartBody.Part?,
        @PartMap body: HashMap<String, RequestBody>
    ): Call
    <BasicResponse>

    @GET("app/diarys")
    fun getCalendar(
        @Query("year") year: Int,
        @Query("month") month : Int ): Call<DayResponse>

    @GET("app/diarys/detail")
    fun getDiary(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
    ): Call<ReadDiaryResponse>

    @GET("app/diarys/share")
    fun getDiaries(
        @Query("page") page: Int,
    ): Call<ReceivedDiarysResponse>

    @GET("app/diarys/answer")
    fun getAnswers(
        @Query("page") page: Int,
    ): Call<ReceivedAnswersResponse>

    @POST("app/diarys/answer")
    fun sendReply(
        @Body reply: Reply
    ): Call<BasicResponse>

    @GET("app/diarys/share/{diaryIdx}")
    fun receivedDiary(
        @Path ("diaryIdx") diaryIdx: Int,
    ): Call<ReceivedDiaryResponse>

    @GET("app/diarys/answer/{answerIdx}")
    fun receivedAnswer(
        @Path ("answerIdx") answerIdx: Int,
    ): Call<ReceivedAnswerResponse>

    @PATCH("app/diarys/answer/{answerIdx}")
    fun rejectAnswer(
        @Path ("answerIdx") answerIdx: Int,
    ): Call<BasicResponse>

    @PATCH("app/diarys/{diaryIdx}")
    fun editDiary(
        @Path("diaryIdx") diaryIdx: Int,
        @Body diaryEditBody: DiaryEditRequest
    ): Call<BasicResponse>

    @POST("app/chat")
    fun startChat(
        @Body chatUserIdx: Int,
    ): Call<BasicResponse>

    @GET("app/chat")
    fun getChatList(): Call<ChatListResponse>

    @POST("app/admin/report")
    fun sendReport(
        @Body report: Report
    ): Call<BasicResponse>
}
