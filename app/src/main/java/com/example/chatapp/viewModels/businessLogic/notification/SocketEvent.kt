package com.example.chatapp.viewModels.businessLogic.notification

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.chatapp.App
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.viewModels.network.ConnectivityState
import com.example.chatapp.viewModels.network.NetConnectivity
import com.example.chatapp.viewModels.network.State
import com.example.chatapp.viewModels.notifications.PushNotification
import com.google.gson.Gson
import io.socket.client.Socket
import javax.inject.Inject

abstract class SocketEvent(): ViewModel() {
    protected var mSocket: Socket = SocketCon.getSocket()
    protected lateinit var token: String


    init {

        Session.getToken()?.let { token = it }
        eventListener()
        getConnectivity()
    }

    private fun getConnectivity() {}

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
    abstract fun isConnectivityAvailable(state: State)
}