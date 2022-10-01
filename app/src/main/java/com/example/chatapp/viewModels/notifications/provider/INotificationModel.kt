package com.example.chatapp.viewModels.notifications.provider

import com.example.chatapp.repositoryApi.models.NotificationModel
import com.example.chatapp.repositoryApi.models.NotificationResponse

interface INotificationModel {
    suspend fun getNotification(id: String): MutableList<NotificationModel>?
    suspend fun acceptNotification(notification: NotificationModel) : NotificationResponse?
    suspend fun rejectNotification(notification: NotificationModel) : NotificationResponse?
}