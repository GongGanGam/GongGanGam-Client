package com.example.gonggangam.diaryService

import com.example.gonggangam.model.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part

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

data class DayResponseList (
    @SerializedName("previous") val previous: ArrayList<DayCell>,
    @SerializedName("current") val current: ArrayList<DayCell>,
    @SerializedName("next") val next: ArrayList<DayCell>,
)

data class DayResponse(
    @SerializedName("isSuccess") //성공여부
    val isSuccess: Boolean,
    @SerializedName("code") //코드
    val code: Int,
    @SerializedName("message") //성공여부
    val message: String,
    @SerializedName("result") //날짜
    val result: DayResponseList, //정보가 저장된 날짜 리스트
)

data class DayCell(
    @SerializedName("day") //날짜
    val day: String,
    @SerializedName("emoji") //날짜에 저장된 이모지
    val emoji: String,
)

data class WriteDiary (
    @SerializedName("emoji") val emoji: String,
    @SerializedName("year") val year: Int,
    @SerializedName("month") val month: Int,
    @SerializedName("day") val day: Int,
    @SerializedName("content") val content: String,
    @SerializedName("shareAgree") val shareAgree: String,
    @Part val uploadImg: MultipartBody.Part?,
)

data class ReadDiaryResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ReadDiary
)

data class ReadDiary(
    @SerializedName("diaryIdx") val diaryIdx: Int,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("diaryDate") val diaryDate: String,
    @SerializedName("contents") val contents: String,
    @SerializedName("image") val image: String?,
    @SerializedName("answer") val answer: ReadAnswer?
)

data class ReadAnswer(
    @SerializedName("answerIdx") val answerIdx: Int,
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("userProfImg") val userProfImg: String,
    @SerializedName("answerTime") val answerTime: String,
    @SerializedName("answerContents") val answerContent: String,
)

// basic response
data class BasicResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)

data class Report (
    @SerializedName("reportType") val reportType: String,
    @SerializedName("idxOfType") val idxOfType: Int,
    @SerializedName("reportDetail") val reportDetail: Int,
    @SerializedName("reportContent") val reportContent: String?,
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
    @SerializedName("result") val result: ReceivedAnswerResult?
)

data class ReceivedAnswerResult(
    @SerializedName("diary") val diary: BasicDiary,
    @SerializedName("answer") val answer: ReceivedAnswer,
)

data class ChatListResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<ChatList>
)
