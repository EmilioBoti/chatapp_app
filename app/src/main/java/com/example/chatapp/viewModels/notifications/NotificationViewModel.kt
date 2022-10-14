package com.example.chatapp.viewModels.notifications

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.helpers.Session
import com.example.chatapp.repositoryApi.ApiProvider
import com.example.chatapp.repositoryApi.Repository
import com.example.chatapp.repositoryApi.models.MessageModel
import com.example.chatapp.repositoryApi.models.NotificationModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application): SocketEvent(application), INotificationViewModel {

    var listNotification: MutableLiveData<MutableList<NotificationModel>> = MutableLiveData<MutableList<NotificationModel>>()
    private var provider: Repository = ApiProvider()
    private var currentUser: String? = null
    private val pushNotification: PushNotification = PushNotification(application.applicationContext)

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
           provider.acceptNotification(notification)
        }
    }

    override fun rejectNotification(notification: NotificationModel) {
        viewModelScope.launch {
            provider.rejectNotification(notification)
            listNotification.value?.remove(notification)
            listNotification.postValue(listNotification.value)
        }
    }

    override fun receiveMessage(message: MessageModel) {

    }

    override fun receiveNotifications(notification: HashMap<String, String>) {
        pushNotification.showNotification(notification)
    }

}