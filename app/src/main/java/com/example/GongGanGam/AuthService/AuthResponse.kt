package com.example.gonggangam.AuthService

import com.google.gson.annotations.SerializedName

data class Auth (@SerializedName("userIdx") val userIdx: Int,
                 @SerializedName("jwt") val jwt: String
)

data class AuthResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Auth?
)

data class BasicResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)
