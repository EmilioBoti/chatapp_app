package com.example.chatapp.viewModels.businessLogic.notification

import androidx.lifecycle.ViewModel
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.viewModels.network.State
import io.socket.client.Socket

abstract class SocketEvent(): ViewModel() {
    protected var mSocket: Socket = SocketCon.getSocket()
    protected var token: String = Session.getToken()


    init {
        eventListener()
        getConnectivity()
    }

    private fun getConnectivity() {}

    private fun eventListener() {}

    abstract fun receiveMessage(message: MessageModel)
    abstract fun receiveNotifications(notification: HashMap<String, String>)
    abstract fun isConnectivityAvailable(state: State)
}