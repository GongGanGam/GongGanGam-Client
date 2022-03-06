package com.example.gonggangam.Class

data class ChatModel (
    var users: HashMap<String, Boolean> = HashMap(),
    val comments : HashMap<String, Comment> = HashMap())


