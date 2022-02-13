package com.example.gonggangam

import kotlin.collections.HashMap

data class ChatModel (
    var users: HashMap<String, Boolean> = HashMap(),
    val comments : HashMap<String, Comment> = HashMap())


