package com.example.chatapp.viewModels.home

import android.app.Activity
import android.content.Context

interface IHomeViewModel {
    fun updateSocket(id: String)
    fun getContacts()
    fun getLocalContacts()
    fun logout(activity: Activity)
}