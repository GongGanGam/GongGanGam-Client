package com.example.gonggangam.Class

import java.io.Serializable

data class Diary(
    var diaryIdx: Int? = null,
    var userIdx: Int? =null,
    var userNickname: String? = "",
    var userProfImg: String? = null,
    var diaryContents: String? = "",
    var diaryDate: String? = "",
    var isRead: Char? = 'F',
):Serializable
