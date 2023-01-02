package com.example.chatapp.viewModels.notifications.provider

import com.example.chatapp.remoteRepository.Repository
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse


class NotificationUseCase(private val provider: Repository): INotificationUseCase {

    override suspend fun getNotification(token: String): MutableList<NotificationModel>? {
        return provider.getNotification(token)
    }

    override suspend fun acceptNotification(token: String, notification: NotificationModel): NotificationResponse? {
        return provider.acceptNotification(token,notification)
    }

    override suspend fun rejectNotification(token: String, notification: NotificationModel): NotificationResponse? {
        return provider.rejectNotification(token, notification)
    }
}