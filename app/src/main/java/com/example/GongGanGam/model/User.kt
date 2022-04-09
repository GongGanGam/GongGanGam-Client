package com.example.gonggangam.Class

import java.io.Serializable

data class User(
    var nickname: String? =null,
    var profImg: String?=null,
    var uid: Int?=null,
):Serializable
