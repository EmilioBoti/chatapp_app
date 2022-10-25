package com.example.chatapp.viewModels.notifications

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.App
import com.example.chatapp.helpers.Session
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationViewModel(application: Application): SocketEvent(application), INotificationViewModel {

    @Inject
    lateinit var provider: RemoteDataProvider
    var listNotification: MutableLiveData<MutableList<NotificationModel>> = MutableLiveData<MutableList<NotificationModel>>()
    private var currentUser: String? = null
    private val pushNotification: PushNotification = PushNotification(application.applicationContext)

    init {
        (application as App).getComponent().inject(this)
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