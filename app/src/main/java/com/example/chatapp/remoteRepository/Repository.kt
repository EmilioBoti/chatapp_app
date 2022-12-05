package com.example.chatapp.remoteRepository

import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.LoginResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.remoteRepository.models.FriendEntity
import retrofit2.Call

interface Repository {
    fun getMessages(token: String, roomId: String): Call<MutableList<MessageModel>>
    fun getUserContacts(currentUser: String): Call<FriendEntity>
    fun login(userLogin: UserLogin): Call<LoginResponse>
    fun signIn(newUser: HashMap<String, String>): Call<LoginResponse>
    fun searchNewUser(value: String) : Call<MutableList<UserModel>>

    suspend fun getNotification(id: String): MutableList<NotificationModel>?
    suspend fun acceptNotification(notification: NotificationModel) : NotificationResponse?
    suspend fun rejectNotification(notification: NotificationModel) : NotificationResponse?
}