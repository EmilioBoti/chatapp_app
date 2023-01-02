package com.example.chatapp.viewModels.notifications.provider

import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse

interface INotificationUseCase {
    suspend fun getNotification(token: String): MutableList<NotificationModel>?
    suspend fun acceptNotification(token: String, notification: NotificationModel) : NotificationResponse?
    suspend fun rejectNotification(token: String, notification: NotificationModel) : NotificationResponse?
}