package com.example.chatapp.views.ui.notification

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.chatapp.R
import com.example.chatapp.factory.adapter.ModelViewHolder
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.NotificationModel

class NotificationViewHolder(itemView: View, private val listener: OnClickItem?): ModelViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.userName)
    private val accept: AppCompatTextView? = itemView.findViewById(R.id.accept)
    private val remove: ImageView? = itemView.findViewById(R.id.remove)

    override fun <T> bindData(value: T) {
        val notification: NotificationModel = value as NotificationModel
        name.text = itemView.context.getString(R.string.smsNotification).replaceRange(0, 5, notification.name)


        if (notification.state == true) {
            accept?.apply {
                text = itemView.context.getString(R.string.friend)
                background = ContextCompat.getDrawable(itemView.context, R.drawable.radius_corner_added_btn)
            }
        }

        accept?.setOnClickListener { view ->
            listener?.onAccept(notification, view, absoluteAdapterPosition)
        }

        remove?.setOnClickListener { view ->
            listener?.onReject(notification, view, absoluteAdapterPosition)
        }


    }

}