package com.example.chatapp.viewModels.chat

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.google.gson.Gson
import io.socket.client.Socket

class ChatViewModel: ViewModel(), IChat {
    private lateinit var mSocket: Socket
    private var bundle: Bundle? = null
    private lateinit var context: Context
    private var currentUser: String? = null
    val ROOMID = "roomId"
    val FROM = "from"
    val TO = "to"
    val MESSAGE = "message"

    override fun setUpSocket(bundle: Bundle?, context: Context) {
        mSocket = SocketCon.getSocket()
        this.bundle = bundle
        this.context = context
        currentUser = Session.getUserId(context)


        mSocket.on("message") {
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