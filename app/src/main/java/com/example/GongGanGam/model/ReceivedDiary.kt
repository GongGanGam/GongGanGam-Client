package com.example.GongGanGam.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReceivedDiary (
    var userIdx: Int = 0,
    var userNickname:  String? = "",
    var userProfImg:  String? = "",
    var diaryIdx: Int = 0,
    var diaryDate:  String? = "",
    var diaryContent:  String? = "",
    var diaryImg:  String? = "",
    var answerValid:  String? = "",
): Serializable
