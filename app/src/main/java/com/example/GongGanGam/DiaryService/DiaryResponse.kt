package com.example.gonggangam.DiaryService

import com.example.gonggangam.Class.*
import com.google.gson.annotations.SerializedName

data class Page (
    val curPage: Int,
    val totalPage: Int,
    val pageSize: Int,
)

// basic response
data class BasicResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)

data class Reply (
    @SerializedName("content") val content: String,
    @SerializedName("diaryIdx") val diaryIdx: Int,
)

data class ReceivedDiaryList (
    @SerializedName("page") val page: Page?,
    @SerializedName("diarys") val diarys: ArrayList<Diary>
)

data class ReceivedAnswerList (
    @SerializedName("page") val page: Page?,
    @SerializedName("answers") val answers: ArrayList<Answer>
)

//data class DiaryOne(
//    @SerializedName("diaryIdx") val diaryIdx: Int,
//    @SerializedName("userIdx") val userIdx: Int,
//    @SerializedName("userNickname") val userNickname: String,
//    @SerializedName("userProfImg") val userProfImg: String,
//    @SerializedName("diaryContent") val diaryContent: String,
//    @SerializedName("diaryDate") val diaryDate: String,
//    @SerializedName("diaryImg") val diaryImg: String,
//    @SerializedName("answerValid") val answerValid: String = "",
//)

data class AnswerOne(
    @SerializedName("diary") val diary: BasicDiary,
    @SerializedName("answer") val answer: ReceivedAnswer
)


// 받은 일기 리스트
data class ReceivedDiarysResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ReceivedDiaryList?
)

// 받은 답장 리스트
data class ReceivedAnswersResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ReceivedAnswerList?
)

// 받은 일기 1개 조회
data class ReceivedDiaryResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ReceivedDiary?
)

// 받은 답장 1개 조회
data class ReceivedAnswerResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: AnswerOne?
)
