package com.example.chatapp.viewModels.chat

import android.os.Bundle
import com.example.chatapp.remoteRepository.models.MessageModel
import retrofit2.Call

interface IChat {
    interface Presenter {
        fun setUp(bundle: Bundle?)
        fun getMessages()
        fun sendMessage(text: String)
        fun getContactMessage()
    }

    interface ModelPresenter {
        fun getMessages(roomId: String): Call<MutableList<MessageModel>>
    }
}