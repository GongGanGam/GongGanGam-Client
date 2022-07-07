package com.example.gonggangam.myPageService

import com.example.gonggangam.model.NoticeListData
import com.example.gonggangam.model.UserResult
import com.google.gson.annotations.SerializedName

data class UserResponse (
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: UserResult?
)

data class NoticeResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: NoticeListData?
)