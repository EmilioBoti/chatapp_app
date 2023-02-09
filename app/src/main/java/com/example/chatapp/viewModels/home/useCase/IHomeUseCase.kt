package com.example.chatapp.viewModels.home.useCase

import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.repositoryLocal.database.entity.UserEntity
import com.example.chatapp.viewModels.login.IResponseProvider

interface IHomeUseCase {
    fun getUserContact(token: String, res: IResponseProvider)
    suspend fun getUserContactLocal(token: String) : MutableList<UserModel>
    suspend fun updateAllUsers(users: MutableList<UserEntity>)
}