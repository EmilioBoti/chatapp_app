package com.example.chatapp.viewModels.notifications

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.App
import com.example.chatapp.helpers.Session
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse
import com.example.chatapp.repositoryLocal.database.AppDataBase
import com.example.chatapp.repositoryLocal.database.dao.ChatDao
import com.example.chatapp.repositoryLocal.database.entity.UserEntity
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationViewModel(application: Application): SocketEvent(application), INotificationViewModel {

    @Inject
    lateinit var provider: RemoteDataProvider
    @Inject
    lateinit var db: AppDataBase
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
        val list: MutableList<NotificationModel>? = provider.getNotification(token)
        list?.let { users ->
            listNotification.postValue(users)
        }
    }

    override fun acceptNotification(notification: NotificationModel, position: Int) {
        if (notification.state != true) {
            listNotification.value?.get(position)?.state = true
            viewModelScope.launch {
                val  resp: NotificationResponse? = provider.acceptNotification(token, notification)
                resp?.roomId?.let { id ->
                    val user = UserEntity(notification.fromU, notification.name, notification.email, id,  notification.toU)
                    db.getChatDao().insertUser(user)
                }
                listNotification.postValue(listNotification.value)
            }
        }
    }

    override fun rejectNotification(notification: NotificationModel, position: Int) {
        viewModelScope.launch {
            provider.rejectNotification(notification)
            listNotification.value?.remove(notification)
            listNotification.postValue(listNotification.value)
        }
    }

    override fun receiveMessage(message: MessageModel) {
        pushNotification.showSmsNotification(message)
    }

    override fun receiveNotifications(notification: HashMap<String, String>) {
        pushNotification.showNotification(notification)
    }

}