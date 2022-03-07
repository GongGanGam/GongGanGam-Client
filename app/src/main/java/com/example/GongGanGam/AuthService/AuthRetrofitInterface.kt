package com.example.gonggangam.AuthService

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class loginBody(
    @SerializedName("accessToken") val accessToken: String
)
interface AuthRetrofitInterface {
    @POST("app/users/login/naver")
    fun naverLogin(
        @Body body: loginBody
    ): Call<AuthResponse>
}

//@FormUrlEncoded
//@POST("app/users/login/naver")
//fun naverLogin(
//    @FieldMap body: HashMap<String, String>
//): Call<AuthResponse>
