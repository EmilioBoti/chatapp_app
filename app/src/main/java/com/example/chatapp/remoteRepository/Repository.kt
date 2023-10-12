package com.example.chatapp.remoteRepository

import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse
import com.example.chatapp.viewModels.login.IResponseProvider
import retrofit2.Call
import retrofit2.Response

interface Repository: IChatRepository {
    fun getMessages(token: String, roomId: String, res: IResponseProvider)
    fun getUserContacts(token: String, res: IResponseProvider)
    suspend fun login(userLogin: UserLogin): Response<AuthApiResponse>
    suspend fun signIn(newUser: HashMap<String, String>): Response<AuthApiResponse>
    fun searchNewUser(token: String, value: String, res: IResponseProvider)

    suspend fun getNotification(token: String): MutableList<NotificationModel>?
    suspend fun acceptNotification(token: String, notification: NotificationModel) : NotificationResponse?
    suspend fun rejectNotification(token: String, notification: NotificationModel) : NotificationResponse?
}