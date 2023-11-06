package com.example.chatapp.viewModels.messengerChat.useCase

import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.repositoryLocal.database.AppDataBase
import com.example.chatapp.repositoryLocal.database.entity.MessageEntity
import com.example.chatapp.repositoryLocal.database.entity.convertToMessageModel
import retrofit2.Response
import javax.inject.Inject

class ChatUseCase @Inject constructor() : IChatUseCase {

    @Inject
    lateinit var chatProvider: RemoteDataProvider

    @Inject
    lateinit var db: AppDataBase

    override suspend fun getMessages(roomId: String, token: String): Response<MutableList<MessageModel>> {
        return chatProvider.getMessages(roomId, token)
    }

    override suspend fun getLocalMessage(roomId: String): MutableList<MessageModel> {
        return db.getChatDao().getRoomMessages(roomId).map { it.convertToMessageModel() } as MutableList
    }

    override suspend fun saveMessageLocal(message: MessageEntity) {
        db.getChatDao().insertMessage(message)
    }

    override suspend fun saveAllSmsLocal(listSms: MutableList<MessageEntity>) {
        db.getChatDao().insertAllMessage(listSms)
    }


}