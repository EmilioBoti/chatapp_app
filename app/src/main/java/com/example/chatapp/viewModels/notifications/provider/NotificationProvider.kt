package com.example.chatapp.viewModels.notifications.provider

import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NotificationProvider: INotificationModel {

    override suspend fun getNotification(id: String): MutableList<NotificationModel>? {
        val retrofit = Utils.getRetrofitBuilder().create(ApiEndPoint::class.java)

        return try {
            withContext(Dispatchers.IO) {
                retrofit.getNotifications(id).execute().body()
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun acceptNotification(notification: NotificationModel): NotificationResponse? {
        val retrofit = Utils.getRetrofitBuilder().create(ApiEndPoint::class.java)

        return try {
            withContext(Dispatchers.IO) {
                retrofit.acceptNotification(notification).execute().body()
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun rejectNotification(notification: NotificationModel): NotificationResponse? {
        val retrofit = Utils.getRetrofitBuilder().create(ApiEndPoint::class.java)

        return try {
            withContext(Dispatchers.IO) {
                retrofit.rejectNotification(notification).execute().body()
            }
        } catch (e: Exception) {
            null
        }
    }
}