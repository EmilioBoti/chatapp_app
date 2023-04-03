package com.example.chatapp.viewModels.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.chatapp.viewModels.friend.FriendViewModel
import com.example.chatapp.views.ui.chatRoom.ChatRoom

class Router {


    fun <T> navigateTo(screen: T, activity: Activity, data: Bundle) {
        screen?.let {
            Intent(activity, it::class.java).apply {
                this.putExtra(FriendViewModel.DATA_USER, data)
                activity.startActivity(this)
            }
        }
    }

    fun <T> navigateTo(screen: T, activity: Activity) {
        screen?.let {
            Intent(activity, it::class.java).apply {
                activity.startActivity(this)
            }
        }
    }

}