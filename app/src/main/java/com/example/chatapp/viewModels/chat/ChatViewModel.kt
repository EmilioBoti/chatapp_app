package com.example.chatapp.viewModels.chat

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.helpers.Session
import com.example.chatapp.repositoryApi.chat.ChatProvider
import com.example.chatapp.repositoryApi.chat.MessageModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel(application: Application): SocketEvent(application), IChat.Presenter {
    private var bundle: Bundle? = null
    val listMessages: MutableLiveData<MutableList<MessageModel>> = MutableLiveData<MutableList<MessageModel>>()
    private var chatProvider: IChat.ModelPresenter
    private var currentUser: String? = null

    companion object {
        private const val ROOM_ID = "roomId"
        private const val FROM = "fromU"
        private const val TO = "toU"
        private const val MESSAGE = "message"
        private const val PRIVATE_SMS = "private"
    }

    init {
        currentUser = Session.getUserId(application.applicationContext)
        chatProvider = ChatProvider()

        mSocket.on(MESSAGE) {
            val user = Gson().fromJson(it[0].toString(), MessageModel::class.java)
            if (isRoomUser(user.roomId)) {
                listMessages.value?.add(user)
                listMessages.postValue(listMessages.value)
            }
        }

        mSocket.on("disconnect") {

        }

    }

    override fun getMessages(bundle: Bundle?) {
        this.bundle = bundle

        bundle?.getString(ROOM_ID)?.let {
            chatProvider.getMessages(it).enqueue(object : Callback<MutableList<MessageModel>> {
                override fun onResponse(call: Call<MutableList<MessageModel>>, response: Response<MutableList<MessageModel>>) {
                    if(response.isSuccessful) {
                        listMessages.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<MessageModel>>, t: Throwable) {

                }

            })
        }

    }

    override fun sendMessage(text: String) {
        this.bundle?.let {
            currentUser?.let {currentUser ->
                val map: HashMap<String, String?> = HashMap<String, String?>()
                map[ROOM_ID] = it.getString(ROOM_ID)
                map[FROM] = currentUser
                map[TO] = it.getString(Session.ID)
                map[MESSAGE] = text

                mSocket.emit(PRIVATE_SMS, Gson().toJson(map))
            }
        }
    }

    private fun isRoomUser(roomId: String): Boolean {
        return roomId == bundle?.getString(ROOM_ID)
    }

}