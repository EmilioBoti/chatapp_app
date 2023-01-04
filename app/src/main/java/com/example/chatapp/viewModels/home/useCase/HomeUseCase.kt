package com.example.chatapp.viewModels.home.useCase

import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.repositoryLocal.database.AppDataBase
import com.example.chatapp.repositoryLocal.database.entity.UserEntity
import com.example.chatapp.repositoryLocal.database.entity.convertToUserModel
import com.example.chatapp.viewModels.login.IResponseProvider
import javax.inject.Inject

class HomeUseCase @Inject constructor(): IHomeUseCase {

    @Inject
    lateinit var provider: RemoteDataProvider

    @Inject
    lateinit var db: AppDataBase

    override fun getUserContact(token: String, res: IResponseProvider) {
        provider.getUserContacts(token, res)
    }

    override suspend fun getUserContactLocal(token: String): MutableList<UserModel> {
        return db.getChatDao().getAllContacts(token).map { it.convertToUserModel() } as MutableList
    }

    override suspend fun updateAllUsers(users: MutableList<UserEntity>) {
        db.getChatDao().insertAllUser(users)
    }

}