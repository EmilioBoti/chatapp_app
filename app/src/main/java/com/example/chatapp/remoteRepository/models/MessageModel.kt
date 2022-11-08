package com.example.chatapp.remoteRepository.models

import com.example.chatapp.repositoryLocal.database.entity.MessageEntity

data class MessageModel(
    val messageId: String,
    val roomId: String,
    val fromU: String,
    val toU: String,
    val userName: String,
    val message: String,
    var times: String,
    )

fun MessageModel.convertToMessageEntity(): MessageEntity = MessageEntity(messageId, userName, roomId, fromU, toU, message, times)
