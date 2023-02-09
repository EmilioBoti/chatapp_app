package com.example.chatapp.viewModels.chat.useCase

import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.repositoryLocal.database.AppDataBase
import com.example.chatapp.repositoryLocal.database.entity.MessageEntity
import com.example.chatapp.repositoryLocal.database.entity.convertToMessageModel
import com.example.chatapp.viewModels.login.IResponseProvider
import javax.inject.Inject

class ChatUseCase @Inject constructor() : IChatUseCase {

    @Inject
    lateinit var chatProvider: RemoteDataProvider

    @Inject
    lateinit var db: AppDataBase

    override fun getMessages(roomId: String, token: String, res: IResponseProvider) {
        chatProvider.getMessages(roomId, token, res)
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