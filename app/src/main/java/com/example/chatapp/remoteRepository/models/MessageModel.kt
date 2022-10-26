package com.example.chatapp.remoteRepository.models

data class MessageModel(
    val id: String,
    val roomId: String,
    val messageId: String,
    val fromU: String,
    val toU: String,
    val userName: String,
    val message: String,
    val times: String,
    )