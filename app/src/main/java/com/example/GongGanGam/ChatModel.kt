package com.example.GongGanGam

import java.util.*
import kotlin.collections.HashMap

class ChatModel {
    var users = HashMap<String, Boolean>()
    var comments = HashMap<String, Comment>()

    inner class Comment {
        var uid: String = ""
        var message: String =""
        var timeStamp: Long = 0
    }
}