package com.example.chatapp.remoteRepository

import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.viewModels.login.IResponseProvider
import retrofit2.Response

interface Repository: IChatRepository {
    suspend fun getMessages(token: String, roomId: String): Response<MutableList<MessageModel>>
    suspend fun getUserChats(token: String) : Response<MutableList<UserModel>>
    suspend fun login(userLogin: UserLogin): Response<AuthApiResponse>
    suspend fun signIn(newUser: HashMap<String, String>): Response<AuthApiResponse>
    fun searchNewUser(token: String, value: String, res: IResponseProvider)

    suspend fun getNotification(token: String): MutableList<NotificationModel>?
    suspend fun acceptNotification(token: String, notification: NotificationModel) : NotificationResponse?
    suspend fun rejectNotification(token: String, notification: NotificationModel) : NotificationResponse?
}