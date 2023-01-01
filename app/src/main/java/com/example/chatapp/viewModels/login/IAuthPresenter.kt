package com.example.chatapp.viewModels.login

import com.example.chatapp.remoteRepository.models.UserModel

interface IAuthPresenter {
    fun saveSession(user: UserModel)
    fun saveToken(token: String)
    fun setUpSocket(token: String)
}