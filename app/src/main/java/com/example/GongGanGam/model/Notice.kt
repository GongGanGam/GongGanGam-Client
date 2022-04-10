package com.example.GongGanGam.model

data class NoticeModel(
    var title: String,
    var noticeContent: String,
    var noticeDate:String,
    var notices: ArrayList<Notice>
)

data class Notice (
    var title: String? = "",
    var date: String? = "",
    var content: String = "",
    var isExpanded: Boolean = false,
)
