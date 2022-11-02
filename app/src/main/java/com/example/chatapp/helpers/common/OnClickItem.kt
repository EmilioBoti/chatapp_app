package com.example.chatapp.helpers.common

import android.view.View
import com.example.chatapp.remoteRepository.models.NotificationModel

interface OnClickItem {
    fun onClick(pos: Int)
    fun onAccept(notification: NotificationModel, view: View, position: Int)
    fun onReject(notification: NotificationModel, view: View, position: Int)
}