package com.example.chatapp.views.main.chat

import android.os.Bundle

interface IChatViewModel {

    fun getUserChats()
    fun getUserSelected(pos: Int): Bundle

}