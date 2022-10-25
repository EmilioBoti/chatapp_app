package com.example.chatapp.helpers.common

import com.example.chatapp.remoteRepository.models.NotificationModel

interface OnClickItem {
    fun onClick(pos: Int)
    fun onAccept(notification: NotificationModel)
    fun onReject(notification: NotificationModel)
}