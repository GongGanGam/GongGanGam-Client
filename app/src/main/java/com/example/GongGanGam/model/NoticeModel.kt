package com.example.GongGanGam.model

data class NoticeModel(
    val title: String,
    val noticeContent: String,
    val noticeDate: String,
    var isExpanded: Boolean
)