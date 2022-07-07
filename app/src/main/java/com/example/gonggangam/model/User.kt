package com.example.gonggangam.model

import java.io.Serializable

data class User(
    var uid: Int?=null,
    var nickname: String? =null,
    var profImg: String?=null,
):Serializable
