package com.example.chatapp.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.chatapp.views.ui.chatRoom.ChatRoom

class Router {
    companion object {
        const val DATA_USER: String = "data"

        fun navigateTo(activity: Activity, classLoader: ClassLoader, bundle: Bundle?) {
            Intent(activity, classLoader::class.java).apply {
                this.putExtra(DATA_USER, bundle)
                activity.startActivity(this);
            }
        }
    }
}