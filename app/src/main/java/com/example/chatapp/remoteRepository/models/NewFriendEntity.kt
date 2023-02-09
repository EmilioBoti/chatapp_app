package com.example.chatapp.remoteRepository.models

data class NewFriendEntity(
    val id: String,
    val name: String,
    val email: String,
    var isFriend: Boolean?,
    val type: String?
    )
