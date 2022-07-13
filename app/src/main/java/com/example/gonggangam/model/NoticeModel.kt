package com.example.gonggangam.model

data class NoticeModel(
    val title: String,
    val noticeContent: String,
    val noticeDate: String,
    var isExpanded: Boolean
)