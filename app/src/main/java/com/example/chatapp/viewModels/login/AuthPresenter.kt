package com.example.chatapp.viewModels.login

import android.content.Context
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.remoteRepository.models.UserModel

class AuthPresenter(private val context: Context): IAuthPresenter {


    override fun saveSession(user: UserModel) {
        Session.saveUser(context, user)
    }

    override fun saveToken(token: String) {
        Session.saveToken(context, token)
    }

    override fun setUpSocket(token: String) {
        SocketCon.setSocket(token)
    }

}