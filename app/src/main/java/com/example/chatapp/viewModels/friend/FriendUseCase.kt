package com.example.chatapp.viewModels.friend

import com.example.chatapp.remoteRepository.IChatRepository
import com.example.chatapp.remoteRepository.models.FriendEntity

class FriendUseCase(private val repository: IChatRepository): IChatRepository {

    override suspend fun retrieveUserFriends(token: String): FriendEntity? {
        return repository.retrieveUserFriends(token)
    }
}