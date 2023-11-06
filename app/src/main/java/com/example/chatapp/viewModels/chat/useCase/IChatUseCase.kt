package com.example.chatapp.viewModels.chat.useCase

import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.repositoryLocal.database.entity.MessageEntity
import retrofit2.Response

interface IChatUseCase {
    suspend fun getMessages(roomId: String, token: String): Response<MutableList<MessageModel>>
    suspend fun getLocalMessage(roomId: String): MutableList<MessageModel>
    suspend fun saveMessageLocal(message: MessageEntity)
    suspend fun saveAllSmsLocal(listSms: MutableList<MessageEntity>)
}