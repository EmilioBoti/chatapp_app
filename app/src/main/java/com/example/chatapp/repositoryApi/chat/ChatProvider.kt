package com.example.chatapp.repositoryApi.chat

import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.repositoryApi.models.MessageModel
import com.example.chatapp.viewModels.chat.IChat
import retrofit2.Call
import retrofit2.Retrofit

class ChatProvider: IChat.ModelPresenter {

    override fun getMessages(roomId: String): Call<MutableList<MessageModel>> {
        val retrofit: Retrofit = Utils.getRetrofitBuilder()
        return  retrofit.create(ApiEndPoint::class.java).getMessage(roomId)
    }
 }