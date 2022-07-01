package com.example.GongGanGam.myPageService

import com.example.GongGanGam.diaryService.BasicResponse
import com.example.GongGanGam.model.NoticeListData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {
    @GET("app/admin/notice")
    fun getNoticeList(): Call<NoticeResponse>

    @GET("app/users/{userIdx}")
    fun getUser(
        @Path("userIdx") userIdx: Int,
    ): Call<UserResponse>

    @PATCH("app/users/{userIdx}")
    fun editUserInfo(
        @Body nickname: String,
        @Body birthYear: Int,
        @Body setAge: String,
        @Body gender: String,
        @Path("userIdx") userIdx: Int
    ): Call<BasicResponse>

    @Multipart
    @PATCH("app/users/{userIdx}/profile")
    fun editProfileImage(
        @Part profImg: MultipartBody.Part?,
        @Path("userIdx") userIdx: Int,
    ): Call<BasicResponse>

    @PATCH("app/users/{userIdx}/push/diary")
    fun setDiaryPush(
        @Path("userIdx") userIdx: Int,
        @Body diaryPush: String
    ): Call<BasicResponse>

    @PATCH("app/users/{userIdx}/push/answer")
    fun setAnswerPush(
        @Path("userIdx") userIdx: Int,
        @Body answerPush: String
    ): Call<BasicResponse>

    @PATCH("app/users/{userIdx}/push/chat")
    fun setChatPush(
        @Path("userIdx") userIdx: Int,
        @Body chatPush: String
    ): Call<BasicResponse>
}
