package com.example.GongGanGam.model

data class NoticeData(
    val notices: ArrayList<Notice>,
    val page: Page
) {
    data class Page(
        val curPage: Int,
        val pageSize: Int,
        val totalPage: Int
    )
}

data class Notice(
    val noticeContent: String,
    val noticeDate: String,
    val title: String
)