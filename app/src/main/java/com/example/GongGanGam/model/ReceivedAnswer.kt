package com.example.gonggangam.Class

import java.io.Serializable
data class ReceivedAnswer (
    var answerIdx: Int? = null,
    var answerUserIdx: Int? =null,
    var nickname: String? = "",
    var profImg: String? = null,
    var answerContents: String? = "",
    var answerDate: String? = "",
    var isReject: Char? = 'F',
): Serializable