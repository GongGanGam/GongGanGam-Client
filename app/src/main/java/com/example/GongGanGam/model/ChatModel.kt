package com.example.GongGanGam.model

data class ChatModel (
    var users: HashMap<String, Boolean> = HashMap(),
//    var opp: HashMap<String, User> = HashMap(),
    val comments : HashMap<String, Comment> = HashMap()
)


