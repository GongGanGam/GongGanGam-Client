package com.example.gonggangam.DiaryService

import com.example.gonggangam.Class.*
import com.google.gson.annotations.SerializedName

data class Page (
    val curPage: Int,
    val totalPage: Int,
    val pageSize: Int,
)

// 세영님 api
data class DayRequest(
    @SerializedName("year") //년도
    val year: Int,
    @SerializedName("month") //월
    val month: Int,
    )

data class DayResponse(
    @SerializedName("isSuccess") //성공여부
    val isSuccess: Boolean,
    @SerializedName("code") //코드
    val code: Int,
    @SerializedName("message") //성공여부
    val message: String,
    @SerializedName("result") //날짜
    val result: List<DayCell>, //정보가 저장된 날짜 리스트
)

data class DayCell(
    @SerializedName("day") //날짜
    val day: String,
    @SerializedName("emoji") //날짜에 저장된 이모지
    val emoji: String,
)

// basic response
data class BasicResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)

data class WriteResult (
    @SerializedName("isSuccess") //성공여부
    val isSuccess: Boolean,
    @SerializedName("code") //코드
    val code: Int,
    @SerializedName("message") //메세지
    val message: String,
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
