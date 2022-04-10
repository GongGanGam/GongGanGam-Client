package com.example.GongGanGam.myPageService

import com.example.GongGanGam.model.UserResult
import com.google.gson.annotations.SerializedName

data class UserResponse (
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: UserResult?
        )
