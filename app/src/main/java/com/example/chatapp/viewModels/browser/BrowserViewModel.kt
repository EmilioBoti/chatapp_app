package com.example.chatapp.viewModels.browser

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.helpers.Session
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.NewFriendEntity
import com.example.chatapp.viewModels.browser.useCase.IBrowserUseCase
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.login.ErrorLogin
import com.example.chatapp.viewModels.login.IResponseProvider
import com.example.chatapp.viewModels.network.State
import com.example.chatapp.viewModels.notifications.PushNotification
import com.google.gson.Gson

class BrowserViewModel(private val provider: IBrowserUseCase,
                       application: Application) : SocketEvent(application), IBrowserPresenter {

    val listUserFound: MutableLiveData<MutableList<NewFriendEntity>> = MutableLiveData<MutableList<NewFriendEntity>>()
    private lateinit var userId: String
    private lateinit var userName: String
    private val TO: String = "toU"
    private val FROM: String = "fromU"
    private val NAME: String = "name"
    private val SOCKETID: String = "toSocketId"
    private val NOTIFICATION: String = "notification"
    private var context: Application = application
    private val pushNotification: PushNotification = PushNotification(application.applicationContext)


    init {

        val map: Map<String, *>? = Session.getSession()
        map?.let {
            userId = map[Session.ID] as String
            userName = map[Session.NAME] as String
        }
    }

    override fun search(value: String) {

        provider.searchNewUser(token, value, object : IResponseProvider {
            override fun <T> response(data: T) {
                val users: MutableList<NewFriendEntity> = data as MutableList<NewFriendEntity>
                listUserFound.postValue(users)
            }

            override fun responseError(err: ErrorLogin) {
            }

        })
    }


    override fun receiveMessage(message: MessageModel) {}

    override fun receiveNotifications(notification: HashMap<String, String>) {
        pushNotification.showNotification(notification)
    }

    override fun isConnectivityAvailable(state: State) {

    }

    override fun sendRequest(pos: Int) {
        listUserFound.value?.get(pos)?.let {
            val data = HashMap<String, String>()
            data[TO] = it.id
//            it.socketId?.let { socket -> data[SOCKETID] = socket }
            data[FROM] = userId
            data[NAME] = userName

            mSocket.emit(NOTIFICATION, Gson().toJson(data))
        }
    }

}