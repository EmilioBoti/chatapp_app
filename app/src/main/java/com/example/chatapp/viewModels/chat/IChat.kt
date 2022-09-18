package com.example.chatapp.viewModels.chat

import android.content.Context
import android.os.Bundle
import com.example.chatapp.repositoryApi.chat.MessageModel
import retrofit2.Call

interface IChat {
    interface Presenter {
        fun setUpSocket(bundle: Bundle?, context: Context)
        fun sendMessage(text: String)
    }

    interface ModelPresenter {
        fun getMessages(roomId: String): Call<MutableList<MessageModel>>
    }
}