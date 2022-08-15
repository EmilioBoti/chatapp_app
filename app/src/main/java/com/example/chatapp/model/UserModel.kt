package com.example.chatapp.model

data class UserModel(
    val roomId:String,
    val id: String,
    val name: String,
    val email: String,
    val socketId: String?,
    var lastMessage: String?
    )
