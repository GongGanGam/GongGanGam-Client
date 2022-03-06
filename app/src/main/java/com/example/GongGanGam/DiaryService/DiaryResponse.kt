package com.example.gonggangam.DiaryService

import com.example.gonggangam.Class.Answer
import com.example.gonggangam.Class.Diary
import com.google.gson.annotations.SerializedName

data class Page (
    val curPage: Int,
    val totalPage: Int,
    val pageSize: Int,
)

data class ReceivedDiary (
    @SerializedName("page") val page: Page?,
    @SerializedName("diarys") val diarys: ArrayList<Diary>
)

data class ReceivedAnswer (
    @SerializedName("page") val page: Page?,
    @SerializedName("answers") val answers: ArrayList<Answer>
)

data class ReceivedDiaryResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ReceivedDiary?
)

data class ReceivedAnswerResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ReceivedAnswer?
)