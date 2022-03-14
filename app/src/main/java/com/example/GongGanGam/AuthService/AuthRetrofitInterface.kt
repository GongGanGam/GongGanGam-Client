package com.example.gonggangam.AuthService

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class loginBody(
    @SerializedName("token") val token: String
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

    )

}

//@FormUrlEncoded
//@POST("app/users/login/naver")
//fun naverLogin(
//    @FieldMap body: HashMap<String, String>
//): Call<AuthResponse>
