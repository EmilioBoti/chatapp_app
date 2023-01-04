package com.example.chatapp.remoteRepository

import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.LoginResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse
import com.example.chatapp.viewModels.login.IResponseProvider
import retrofit2.Call

interface Repository {
    fun getMessages(token: String, roomId: String, res: IResponseProvider)
    fun getUserContacts(token: String, res: IResponseProvider)
    fun login(userLogin: UserLogin, res: IResponseProvider)
    fun signIn(newUser: HashMap<String, String>): Call<LoginResponse>
    fun searchNewUser(token: String, value: String, res: IResponseProvider)

    suspend fun getNotification(token: String): MutableList<NotificationModel>?
    suspend fun acceptNotification(token: String, notification: NotificationModel) : NotificationResponse?
    suspend fun rejectNotification(token: String, notification: NotificationModel) : NotificationResponse?
}