package com.example.chatapp.viewModels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.model.UserModel
import com.example.chatapp.model.home.HomeProvider
import com.google.gson.Gson
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel(), IHomeViewModel {
    val contacts: MutableLiveData<MutableList<UserModel>> = MutableLiveData<MutableList<UserModel>>()
    lateinit var mSocket: Socket
    lateinit var currentUser: String
    lateinit var provider: HomeProvider

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

            }

        })

    }


}