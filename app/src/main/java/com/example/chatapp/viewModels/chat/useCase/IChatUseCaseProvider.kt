package com.example.chatapp.viewModels.chat.useCase

import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.repositoryLocal.database.entity.UserEntity
import com.example.chatapp.viewModels.login.IResponseProvider
import retrofit2.Response

interface IChatUseCaseProvider {
    suspend fun getUserChats(token: String): Response<MutableList<UserModel>>

}