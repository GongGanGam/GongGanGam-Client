package com.example.gonggangam.AuthService

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class loginBody(
    @SerializedName("token") val token: String
)

data class signInBody(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("birthYear") val birthYear: Int,
    @SerializedName("gender") val gender: String
)

interface AuthRetrofitInterface {
    @POST("app/users/login/naver")
    fun naverLogin(
        @Body body: loginBody
    ): Call<AuthResponse>

    @POST("app/users/login/kakao")
    fun kakaoLogin(
        @Body body: loginBody
    ): Call<AuthResponse>

    @POST("app/users/signin")
    fun signIn(
        @Body body: signInBody
    ): Call<BasicResponse>

}

