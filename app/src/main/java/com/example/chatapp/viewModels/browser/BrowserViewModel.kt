package com.example.chatapp.viewModels.browser

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.R
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.repositoryApi.models.UserModel
import com.example.chatapp.repositoryApi.browser.BrowserProvider
import com.example.chatapp.repositoryApi.browser.IBrowserPresenter
import com.example.chatapp.repositoryApi.browser.IBrowserModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.google.gson.Gson
import io.socket.client.Socket
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrowserViewModel(application: Application) : SocketEvent(application), IBrowserPresenter {

    val listUserFound: MutableLiveData<MutableList<UserModel>> = MutableLiveData<MutableList<UserModel>>()
    private var provider: IBrowserModel = BrowserProvider()
    private lateinit var userId: String
    private lateinit var userName: String
    private val channelId: String = "com.example.chatapp"
    private val TO: String = "toU"
    private val FROM: String = "fromU"
    private val NAME: String = "name"
    private val SOCKETID: String = "toSocketId"
    private val NOTIFICATION: String = "notification"
    private val NOTIFY: String = "notify"
    private var context: Application = application

    init {

        val map: Map<String, *>? = Session.getSession(context.applicationContext)
        map?.let {
            userId = map[Session.ID] as String
            userName = map[Session.NAME] as String
        }
    }

    override fun search(value: String) {

        provider.searchNewUser(value).enqueue(object : Callback<MutableList<UserModel>> {

            override fun onResponse(call: Call<MutableList<UserModel>>, response: Response<MutableList<UserModel>>) {
                if (response.isSuccessful) {
                    listUserFound.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<MutableList<UserModel>>, t: Throwable) {

            }

        })

    }

    override fun sendRequest(pos: Int) {
        listUserFound.value?.get(pos)?.let {
            val data = HashMap<String, String>()
            data[TO] = it.id
            it.socketId?.let { socket -> data[SOCKETID] = socket }
            data[FROM] = userId
            data[NAME] = userName

            mSocket.emit(NOTIFICATION, Gson().toJson(data))
        }
    }

}