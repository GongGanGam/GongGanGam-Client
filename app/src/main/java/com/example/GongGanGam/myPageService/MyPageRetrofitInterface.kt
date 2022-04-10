package com.example.GongGanGam.myPageService

import com.example.GongGanGam.model.NoticeModel
import com.example.GongGanGam.myPageService.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MyPageRetrofitInterface {
    @GET("app/admin/notice")
    fun requestAllData(): Call<NoticeModel>


    @GET("app/users/{userIdx}")
    fun getUser(
        @Header("x-access-token") jwt: String,
        @Path("userIdx") userIdx: Int,
    ): Call<UserResponse>
}
