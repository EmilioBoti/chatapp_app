package com.example.chatapp.viewModels.businessLogic.notification

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import com.example.chatapp.R
import com.example.chatapp.api.SocketCon
import com.google.gson.Gson
import io.socket.client.Socket

open class SocketEvent(application: Application): AndroidViewModel(application) {
    protected var mSocket: Socket = SocketCon.getSocket()
    private var context: Application = application

    private val channelId: String = "com.example.chatapp"
    private val TO: String = "toU"
    private val FROM: String = "fromU"
    private val NAME: String = "name"
    private val SOCKETID: String = "toSocketId"
    private val NOTIFICATION: String = "notification"
    private val NOTIFY: String = "notify"

    init {
        mSocket.on(NOTIFY) {
            it[0]?.let {
                val notification = Gson().fromJson<HashMap<String, String>>(it.toString(), HashMap::class.java)
                showNotification(notification)
            }
        }
    }

    protected fun notificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "notification"
            }

            //register channel in the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(data: HashMap<String, String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationBuilder =  Notification.Builder(context, channelId)
                .setSmallIcon(R.drawable.person_add_24)
                //.setContentIntent(pendingIntent)
                .setContentTitle(data[NAME])
                .setContentText("${data[NAME]} want to be friend.")
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(1, notificationBuilder.build())
            }
        }
    }
}