package com.example.chatapp.remoteRepository.models

import com.example.chatapp.repositoryLocal.database.entity.UserEntity

data class UserModel(
    val roomId:String,
    val id: String,
    val name: String,
    val email: String,
    val socketId: String?,
    var lastMessage: String?,
    val toUser: String,
    val state: Boolean?,
    val times: String?
    )


fun UserModel.convertToUserEntity(): UserEntity = UserEntity(id, name, email, roomId, toUser)