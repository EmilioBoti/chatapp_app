package com.example.chatapp.viewModels.businessLogic.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.chatapp.App
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.viewModels.network.ConnectivityState
import com.example.chatapp.viewModels.notifications.PushNotification
import com.google.gson.Gson
import io.socket.client.Socket
import javax.inject.Inject

abstract class SocketEvent(application: Application): AndroidViewModel(application) {
    protected var mSocket: Socket = SocketCon.getSocket()
    private var context: Application = application

    private val pushNotification: PushNotification = PushNotification(context.applicationContext)

    @Inject
    protected lateinit var connectivityState: ConnectivityState

    init {
        eventListener()
        (application as App).getComponent().inject(this)
    }

    private fun eventListener() {
        mSocket.on(Const.NOTIFY) {
            it[0]?.let {
                val notification = Gson().fromJson<HashMap<String, String>>(it.toString(), HashMap::class.java)
                this.receiveNotifications(notification)
            }
        }
        mSocket.on(Const.MESSAGE) {
            val message = Gson().fromJson(it[0].toString(), MessageModel::class.java)
            this.receiveMessage(message)
        }
    }

    abstract fun receiveMessage(message: MessageModel)
    abstract fun receiveNotifications(notification: HashMap<String, String>)
}