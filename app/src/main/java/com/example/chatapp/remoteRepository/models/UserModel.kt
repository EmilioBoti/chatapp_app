package com.example.chatapp.remoteRepository.models


data class UserModel(
    val roomId:String,
    val id: String,
    val name: String,
    val email: String,
    val socketId: String?,
    var lastMessage: String?,
    val state: Boolean?,
    val times: String?
    )
