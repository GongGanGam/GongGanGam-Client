package com.example.GongGanGam.myPageService

import com.example.GongGanGam.diaryService.BasicResponse
import com.example.GongGanGam.model.NoticeModel
import com.example.GongGanGam.myPageService.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {
    @GET("app/admin/notice")
    fun requestAllData(): Call<NoticeModel>


    @GET("app/users/{userIdx}")
    fun getUser(
        @Header("x-access-token") jwt: String,
        @Path("userIdx") userIdx: Int,
    ): Call<UserResponse>

    @PATCH("app/users/{userIdx}")
    fun editUserInfo(
        @Header("x-access-token") jwt: String,
        @Body nickname: String,
        @Body birthYear: Int,
        @Body setAge: Boolean,
        @Body gender: String,
        @Path("userIdx") userIdx: Int
    ): Call<BasicResponse>

    @Multipart
    @PATCH("/app/users/{userIdx}/profile")
    fun editProfileImage(
        @Header("x-access-token") jwt: String,
        @Part profImg: MultipartBody.Part,
        @Path ("userIdx") userIdx: Int,
    ): Call<BasicResponse>

}
