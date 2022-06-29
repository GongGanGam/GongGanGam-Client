package com.example.GongGanGam.model

data class NoticeListData(
    val notices: ArrayList<NoticeData>,
    val page: Page
) {
    data class NoticeData(
        val title: String,
        val noticeContent: String,
        val noticeDate: String
    )

    data class Page(
        val curPage: Int,
        val pageSize: Int,
        val totalPage: Int
    )
}
