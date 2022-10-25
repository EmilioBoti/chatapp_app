package com.example.chatapp.viewModels.notifications.provider

import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse

interface INotificationModel {
    suspend fun getNotification(id: String): MutableList<NotificationModel>?
    suspend fun acceptNotification(notification: NotificationModel) : NotificationResponse?
    suspend fun rejectNotification(notification: NotificationModel) : NotificationResponse?
}