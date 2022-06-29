package com.example.GongGanGam.model

data class NoticeListData(
    val notices: ArrayList<NoticeData>,
    val page: Page
) {
    data class Page(
        val curPage: Int,
        val pageSize: Int,
        val totalPage: Int
    )
}

data class NoticeData(
    val noticeContent: String,
    val noticeDate: String,
    val title: String
)