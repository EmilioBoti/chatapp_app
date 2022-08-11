package com.example.chatapp.viewModels.home

import android.app.Activity
import android.content.Context

interface IHomeViewModel {
    fun updateSocket(id: String)
    fun getContacts(currentUser: String)
    fun logout(activity: Activity)
}