package com.example.GongGanGam.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    var uid: Int?=null,
    var nickname: String? =null,
    var profImg: String?=null,
):Serializable
