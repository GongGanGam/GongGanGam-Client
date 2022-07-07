package com.example.gonggangam.model

import java.io.Serializable

data class BasicDiary (
    var diaryIdx: Int? = null,
    var emoji: String= "",
    var diaryContent: String? = "",
    var diaryDate: String? = "",
): Serializable