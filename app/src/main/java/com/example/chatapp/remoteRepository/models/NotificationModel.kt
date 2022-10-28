package com.example.chatapp.remoteRepository.models

data class NotificationModel(
    val notificationId:String,
    val name: String,
    val fromU: String,
    val toU: String,
    val socketId: String?,
    val email: String,
    var state: Boolean?
)
