package com.example.GongGanGam.authService

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

data class LoginBody(
    @SerializedName("token") val token: String,
    @SerializedName("deviceToken") val deviceToken: String
)

data class SignInBody(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("birthYear") val birthYear: Int,
    @SerializedName("gender") val gender: String
)

interface AuthRetrofitInterface {
    @POST("app/users/login/naver")
    fun naverLogin(
        @Body body: LoginBody
    ): Call<AuthResponse>

    @POST("app/users/login/kakao")
    fun kakaoLogin(
        @Body body: LoginBody
    ): Call<AuthResponse>

    @POST("app/users/signin")
    fun signIn(
        @Body body: SignInBody
    ): Call<BasicResponse>


}

