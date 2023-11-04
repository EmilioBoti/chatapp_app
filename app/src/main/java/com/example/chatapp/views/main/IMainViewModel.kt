package com.example.chatapp.views.main

import android.app.Activity

interface IMainViewModel {
    fun updateSocket(id: String)
    fun getContacts()
    fun getLocalContacts()
    fun logout(activity: Activity)
}