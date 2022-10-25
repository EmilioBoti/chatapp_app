package com.example.chatapp.viewModels.notifications

import com.example.chatapp.remoteRepository.models.NotificationModel

interface INotificationViewModel {
    fun getNotification()
    fun acceptNotification(notification: NotificationModel)
    fun rejectNotification(notification: NotificationModel)
}