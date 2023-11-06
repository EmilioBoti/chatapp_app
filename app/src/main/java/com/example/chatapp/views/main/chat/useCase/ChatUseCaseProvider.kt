package com.example.chatapp.views.main.chat.useCase

import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.Repository
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.viewModels.messengerChat.useCase.IChatUseCaseProvider
import retrofit2.Response


class ChatUseCaseProvider : IChatUseCaseProvider {


    private val remoteProvider: Repository = RemoteDataProvider(Utils.getRetrofitBuilder())

    override suspend fun getUserChats(token: String): Response<MutableList<UserModel>> {
        return remoteProvider.getUserChats(token)
    }

}