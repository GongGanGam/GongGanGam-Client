package com.example.GongGanGam.model

data class NoticeModel(
    var title: String,
    var noticeContent: String,
    var noticeDate:String,
    var notices: ArrayList<Notice>
)

abstract class NoticeBase(
    open var title: String? = "",
    open var date: String? = "",
    open var content: String = ""
)

data class Notice (
    override var title: String? = "",
    override var date: String? = "",
    override var content: String = "",
    var isExpanded: Boolean = false,
) : NoticeBase()
