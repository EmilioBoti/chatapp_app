package com.example.chatapp.viewModels.notifications.provider

import com.example.chatapp.repositoryApi.models.UserModel

interface INotification {
    suspend fun getNotification(id: String): MutableList<UserModel>?
}