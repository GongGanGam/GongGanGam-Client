package com.example.gonggangam.MyPageService

import com.example.gonggangam.Class.NoticeModel
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
