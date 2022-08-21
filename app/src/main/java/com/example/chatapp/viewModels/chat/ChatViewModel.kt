package com.example.chatapp.viewModels.chat

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.model.chat.ChatProvider
import com.example.chatapp.model.chat.MessageModel
import com.google.gson.Gson
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel: ViewModel(), IChat.Presenter {
    private lateinit var mSocket: Socket
    private var bundle: Bundle? = null
    private lateinit var context: Context
    val listMessages: MutableLiveData<MutableList<MessageModel>> = MutableLiveData<MutableList<MessageModel>>()
    private lateinit var chatProvider: ChatProvider
    private var currentUser: String? = null
    val ROOMID = "roomId"
    val FROM = "fromU"
    val TO = "toU"
    val MESSAGE = "message"

    override fun setUpSocket(bundle: Bundle?, context: Context) {
        mSocket = SocketCon.getSocket()
        this.bundle = bundle
        this.context = context
        currentUser = Session.getUserId(context)

        chatProvider = ChatProvider()
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