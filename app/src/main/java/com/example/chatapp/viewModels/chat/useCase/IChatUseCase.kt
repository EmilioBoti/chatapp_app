package com.example.chatapp.viewModels.chat.useCase

import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.repositoryLocal.database.entity.MessageEntity
import com.example.chatapp.viewModels.login.IResponseProvider

interface IChatUseCase {
    fun getMessages(roomId: String, token: String, res: IResponseProvider)
    suspend fun getLocalMessage(roomId: String): MutableList<MessageModel>
    suspend fun saveMessageLocal(message: MessageEntity)
    suspend fun saveAllSmsLocal(listSms: MutableList<MessageEntity>)
}