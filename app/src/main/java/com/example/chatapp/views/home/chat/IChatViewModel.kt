package com.example.chatapp.views.home.chat

import android.os.Bundle

interface IChatViewModel {

    fun getUserChats()
    fun getUserSelected(pos: Int): Bundle

}