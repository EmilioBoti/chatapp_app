package com.example.chatapp.views.ui.notification

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.factory.adapter.ModelViewHolder
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.NotificationModel

class NotificationViewHolder(itemView: View, private val listener: OnClickItem?): ModelViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.userName)
    private val accept: ImageView = itemView.findViewById(R.id.accept)
    private val reject: ImageView = itemView.findViewById(R.id.reject)

    override fun <T> bindData(value: T) {
        val notification: NotificationModel = value as NotificationModel
        name.text = notification.name

        accept.setOnClickListener {
            listener?.onAccept(notification)
        }

        reject.setOnClickListener {
            listener?.onReject(notification)
        }

    }
}