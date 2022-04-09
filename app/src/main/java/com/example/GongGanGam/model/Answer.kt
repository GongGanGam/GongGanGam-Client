package com.example.gonggangam.Class

import java.io.Serializable

data class Answer(
    var answerIdx: Int? = null,
    var userIdx: Int? =null,
    var userNickname: String? = "",
    var userProfImg: String? = null,
    var answerContents: String? = "",
    var answerDate: String? = "",
    var isRead: Char? = 'F',
): Serializable
