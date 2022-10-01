package com.example.chatapp.viewModels.notifications

import com.example.chatapp.repositoryApi.models.NotificationModel
import com.example.chatapp.repositoryApi.models.UserModel

interface INotificationViewModel {
    fun getNotification()
    fun acceptNotification(notification: NotificationModel)
    fun rejectNotification(notification: NotificationModel)
}