package com.example.chatapp.repositoryApi

import com.example.chatapp.repositoryApi.models.MessageModel
import com.example.chatapp.repositoryApi.login.LoginResponse
import com.example.chatapp.repositoryApi.login.UserLogin
import com.example.chatapp.repositoryApi.models.NotificationModel
import com.example.chatapp.repositoryApi.models.NotificationResponse
import com.example.chatapp.repositoryApi.models.UserModel
import retrofit2.Call

interface Repository {
    fun getMessages(roomId: String): Call<MutableList<MessageModel>>
    fun getUserContacts(currentUser: String): Call<MutableList<UserModel>>
    fun login(userLogin: UserLogin): Call<LoginResponse>
    fun signIn(newUser: HashMap<String, String>): Call<LoginResponse>
    fun searchNewUser(value: String) : Call<MutableList<UserModel>>

    suspend fun getNotification(id: String): MutableList<NotificationModel>?
    suspend fun acceptNotification(notification: NotificationModel) : NotificationResponse?
    suspend fun rejectNotification(notification: NotificationModel) : NotificationResponse?
}