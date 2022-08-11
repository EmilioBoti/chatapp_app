package com.example.chatapp.viewModels.chat

import android.content.Context
import android.os.Bundle

interface IChat {
    fun setUpSocket(bundle: Bundle?, context: Context)
    fun sendMessage(text: String)
}