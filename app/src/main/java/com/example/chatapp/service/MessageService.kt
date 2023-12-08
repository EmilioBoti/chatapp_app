package com.example.chatapp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.viewModels.notifications.PushNotification
import com.google.gson.Gson
import io.socket.client.Socket

class MessageService: Service(), IMessengerService {

    private var mSocket: Socket? = null
    private val mBinder: LocalBinder = LocalBinder()
    private var pushNotification: PushNotification = PushNotification(this)

    private var messengerEvent: IMessengerEvent? = null

    interface IMessengerEvent {
        fun onReceiveNewMessage(message: MessageModel)
    }

    init {
        Log.e("myservice", "service is running...")
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    fun init(socket: Socket?, messengerEvent: IMessengerEvent?) {
        this.mSocket = socket
        this.messengerEvent = messengerEvent
        connectToSocket()
        eventListener()
    }

    private fun connectToSocket() {
        mSocket?.let {
            if(!it.connected()) mSocket?.connect()
        }
    }

    override fun sendingMessage(map: HashMap<String, String?>) {
        mSocket?.emit(Const.PRIVATE_SMS, Gson().toJson(map))
    }

    private fun eventListener() {
        mSocket?.on(Const.NOTIFY) {
            it[0]?.let {
                val notification = Gson().fromJson<HashMap<String, String>>(it.toString(), HashMap::class.java)
                pushNotification.showNotification(notification)
            }
        }
        mSocket?.on(Const.MESSAGE) {
            val message = Gson().fromJson(it[0].toString(), MessageModel::class.java)
            Log.e("fromService", message.message)
            messengerEvent?.onReceiveNewMessage(message)
        }
    }

    inner class LocalBinder(): Binder() {
        fun getService(): MessageService = this@MessageService
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.e("myservice", "service is stopped...")
        stopSelf()
    }

}