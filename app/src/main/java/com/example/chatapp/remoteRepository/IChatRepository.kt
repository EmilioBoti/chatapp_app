package com.example.chatapp.remoteRepository

import com.example.chatapp.remoteRepository.models.FriendEntity

interface IChatRepository {
    suspend fun retrieveUserFriends(token: String) : FriendEntity?
}