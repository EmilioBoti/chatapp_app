package com.example.chatapp.viewModels.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import com.example.chatapp.R
import com.example.chatapp.helpers.Session
import com.example.chatapp.repositoryApi.models.MessageModel
import com.example.chatapp.views.ui.chatRoom.ChatRoom

class PushNotification(private val context: Context) {

    private val channelId: String = "com.example.chatapp"
    private val smsChannelId: String = "sms"
    private val NAME: String = "name"

    fun smsNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(smsChannelId, smsChannelId, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "sms"
            }

            //register channel in the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun notificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "notification"
            }

            //register channel in the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showSmsNotification(message: MessageModel) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val bundle: Bundle = Bundle().apply {
                putString(Session.ROOMID, message.roomId)
                putString(Session.ID, message.id)
                putString(Session.NAME, message.userName)
            }

            val intentSms: Intent = Intent(context, ChatRoom::class.java).apply {
                putExtra("data", bundle)
            }

            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intentSms, 0)

            val actionAccept: Notification.Action = Notification.Action
                .Builder(Icon.createWithResource(context, R.drawable.notifications_24), "Accept", null)
                .build()

            val actionDiny: Notification.Action = Notification.Action
                .Builder(Icon.createWithResource(context, R.drawable.notifications_24), "Cancel", null)
                .build()

            val notificationBuilder = Notification.Builder(context, smsChannelId)
                .setSmallIcon(R.drawable.person_add_24)
                .setContentTitle(message.userName)
                .setContentText(message.message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(context)) {
                notify(2, notificationBuilder.build())
            }
        }
    }

    fun showNotification(data: HashMap<String, String>) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationBuilder =  Notification.Builder(context, channelId)
                .setSmallIcon(R.drawable.person_add_24)
                .setContentTitle(data[NAME])
                .setContentText("${data[NAME]} want to be friend.")
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(1, notificationBuilder.build())
            }
        }
    }
}