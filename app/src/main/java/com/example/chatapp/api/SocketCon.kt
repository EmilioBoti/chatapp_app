package com.example.chatapp.api

import android.util.Log
import com.example.chatapp.helpers.utils.Const
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketCon {
    private lateinit var mSocket: Socket

    @Synchronized
    fun setSocket(){
        try {
            mSocket = IO.socket(Const.HOST)
        }catch (err: URISyntaxException){
            Log.e("socketErr", err.reason)
        }
    }
    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }
}