package com.example.gonggangam.MyPageService

import com.example.gonggangam.Class.UserResult
import com.google.gson.annotations.SerializedName

data class UserResponse (
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: UserResult?
        )
