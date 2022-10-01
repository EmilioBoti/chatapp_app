package com.example.chatapp.viewModels.notifications

import android.app.Application
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.R
import com.example.chatapp.helpers.Session
import com.example.chatapp.repositoryApi.models.NotificationModel
import com.example.chatapp.repositoryApi.models.NotificationResponse
import com.example.chatapp.repositoryApi.models.UserModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.notifications.provider.INotificationModel
import com.example.chatapp.viewModels.notifications.provider.NotificationProvider
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class NotificationViewModel(application: Application): SocketEvent(application), INotificationViewModel {

    var listNotification: MutableLiveData<MutableList<NotificationModel>> = MutableLiveData<MutableList<NotificationModel>>()
    private var provider: INotificationModel = NotificationProvider()
    private var currentUser: String? = null

    init {
        currentUser = Session.getUserId(application.applicationContext)
    }

    override fun getNotification() {
        viewModelScope.launch {
            showNotifications()
        }
    }

    private suspend fun showNotifications() {
        currentUser?.let {
            val list: MutableList<NotificationModel>? = provider.getNotification(it)
            list?.let { users ->
                listNotification.postValue(users)
            }
        }
    }

    override fun acceptNotification(notification: NotificationModel) {
        viewModelScope.launch {
           val notificationResponse: NotificationResponse? = provider.acceptNotification(notification)
        }
    }

    override fun rejectNotification(notification: NotificationModel) {
        viewModelScope.launch {
            provider.rejectNotification(notification)
            listNotification.value?.remove(notification)
            listNotification.postValue(listNotification.value)

        }
    }

}