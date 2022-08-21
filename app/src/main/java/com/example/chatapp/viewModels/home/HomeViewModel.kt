package com.example.chatapp.viewModels.home

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.R
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.model.UserModel
import com.example.chatapp.model.chat.MessageModel
import com.example.chatapp.model.home.HomeProvider
import com.example.chatapp.router.Router
import com.example.chatapp.views.ui.BaseActivity
import com.example.chatapp.views.ui.chatRoom.ChatRoom
import com.google.gson.Gson
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel(), IHomeViewModel {
    val contacts: MutableLiveData<MutableList<UserModel>> = MutableLiveData<MutableList<UserModel>>()
    lateinit var mSocket: Socket
    lateinit var currentUser: String
    lateinit var provider: HomeProvider
    val DATA_USER: String = "data"


    override fun updateSocket(id: String) {
        SocketCon.setSocket()
        mSocket = SocketCon.getSocket()
        mSocket.connect()

        mSocket.on("connect") {
            val map = HashMap<String, String>()
            map[Session.ID] = id
            map[Session.SOCKETID] = mSocket.id()

            mSocket.emit("user", Gson().toJson(map))
        }


        mSocket.on("message") {
            val user = Gson().fromJson(it[0].toString(), MessageModel::class.java)
            contacts.value?.forEach {
                if (it.id == user.fromU) {
                    it.lastMessage = user.message
                }
            }
            contacts.postValue(contacts.value)
        }
    }

    override fun getContacts(currentUser: String) {
        this.currentUser = currentUser
        provider = HomeProvider()
        val call = provider.getUserContacts(currentUser)

        call.enqueue(object : retrofit2.Callback<MutableList<UserModel>> {
            override fun onResponse(call: Call<MutableList<UserModel>>, response: Response<MutableList<UserModel>>) {
                if (response.isSuccessful) {
                    contacts.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<MutableList<UserModel>>, t: Throwable) {
                Log.e("error", "", t.cause)
            }

        })

    }

    fun disconnectSocket() {
        mSocket.disconnect()
    }

    fun navigateChatRoom(activity: Activity, pos: Int) {
        val userModel: UserModel? = contacts.value?.get(pos)
        val data: Bundle = Bundle().apply {
            putString(Session.ROOMID, userModel?.roomId)
            putString(Session.ID, userModel?.id)
            putString(Session.NAME, userModel?.name)
            putString(Session.SOCKETID, userModel?.socketId)
        }
        Intent(activity, ChatRoom::class.java).apply {
            this.putExtra(DATA_USER, data)
            activity.startActivity(this);
        }
    }

    override fun logout(activity: Activity) {
        Session.logout(activity.applicationContext)
        Intent(activity, BaseActivity::class.java).apply {
            activity.startActivity(this)
        }
        activity.finish()
    }


}