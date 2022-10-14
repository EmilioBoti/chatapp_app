package com.example.chatapp.viewModels.businessLogic.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.repositoryApi.models.MessageModel
import com.example.chatapp.viewModels.notifications.PushNotification
import com.google.gson.Gson
import io.socket.client.Socket

abstract class SocketEvent(application: Application): AndroidViewModel(application) {
    protected var mSocket: Socket = SocketCon.getSocket()
    private var context: Application = application

    private val pushNotification: PushNotification = PushNotification(context.applicationContext)


    init {
        eventListener()
    }

    private fun eventListener() {
        mSocket.on(Const.NOTIFY) {
            it[0]?.let {
                val notification = Gson().fromJson<HashMap<String, String>>(it.toString(), HashMap::class.java)
                this.receiveNotifications(notification)
            }
        }
        mSocket.on(Const.MESSAGE) {
            val user = Gson().fromJson(it[0].toString(), MessageModel::class.java)
            this.receiveMessage(user)
        }
    }

    abstract fun receiveMessage(message: MessageModel)
    abstract fun receiveNotifications(notification: HashMap<String, String>)
}