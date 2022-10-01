package com.example.chatapp.viewModels.chat

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.repositoryApi.chat.ChatProvider
import com.example.chatapp.repositoryApi.chat.MessageModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.google.gson.Gson
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel(application: Application): SocketEvent(application), IChat.Presenter {
    private var bundle: Bundle? = null
    val listMessages: MutableLiveData<MutableList<MessageModel>> = MutableLiveData<MutableList<MessageModel>>()
    private var chatProvider: IChat.ModelPresenter
    private var currentUser: String? = null
    private val ROOMID = "roomId"
    private val FROM = "fromU"
    private val TO = "toU"
    private val MESSAGE = "message"

    init {
        currentUser = Session.getUserId(application.applicationContext)
        chatProvider = ChatProvider()

        mSocket.on("message") {
            val user = Gson().fromJson(it[0].toString(), MessageModel::class.java)
            if (user.roomId == bundle?.getString(ROOMID)) {
                listMessages.value?.add(user)
                listMessages.postValue(listMessages.value)
            }
        }

        mSocket.on("disconnect") {
            it[0]
        }

    }

    override fun setUpSocket(bundle: Bundle?, context: Context) {
        mSocket = SocketCon.getSocket()
        this.bundle = bundle

        bundle?.getString(ROOMID)?.let {
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
                map[ROOMID] = it.getString(ROOMID)
                map[FROM] = currentUser
                map[TO] = it.getString(Session.ID)
                map[MESSAGE] = text

                mSocket.emit("private", Gson().toJson(map))
            }
        }
    }


}