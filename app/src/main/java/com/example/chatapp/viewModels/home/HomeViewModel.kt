package com.example.chatapp.viewModels.home

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.App
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.repositoryApi.RemoteDataProvider
import com.example.chatapp.repositoryApi.models.UserModel
import com.example.chatapp.repositoryApi.models.MessageModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.notifications.PushNotification
import com.example.chatapp.views.home.BaseActivity
import com.example.chatapp.views.ui.chatRoom.ChatRoom
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel(application: Application): SocketEvent(application), IHomeViewModel {
    @Inject
    lateinit var provider: RemoteDataProvider
    val contacts: MutableLiveData<MutableList<UserModel>> = MutableLiveData<MutableList<UserModel>>()
    private var currentUser: String? = null
    private val pushNotification: PushNotification = PushNotification(application.applicationContext)

    companion object {
        private const val DATA_USER: String = "data"
    }

    init {
        (application as App).getComponent().inject(this)
        pushNotification.notificationChannel()
        pushNotification.smsNotificationChannel()
        currentUser = Session.getUserId(application.applicationContext)
    }

    override fun updateSocket(id: String) {
        if(mSocket.connected()) mSocket.connect()

        mSocket.on("connect") {
            val map = HashMap<String, String>()
            map[Session.ID] = id
            map[Session.SOCKETID] = mSocket.id()

            mSocket.emit("user", Gson().toJson(map))
        }

    }

    override fun getContacts() {

        currentUser?.let {
            provider.getUserContacts(it).enqueue(object : retrofit2.Callback<MutableList<UserModel>> {
                override fun onResponse(call: Call<MutableList<UserModel>>, response: Response<MutableList<UserModel>>) {
                    if (response.isSuccessful) {
                        contacts.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<UserModel>>, t: Throwable) {
                    Log.e("error", t.message, t.cause)
                }

            })
        }
    }

    override fun receiveMessage(message: MessageModel) {
        if (currentUser != message.fromU) { pushNotification.showSmsNotification(message) }
        contacts.value?.forEach {
            if (it.id == message.fromU) it.lastMessage = message.message
        }
        contacts.postValue(contacts.value)
    }

    override fun receiveNotifications(notification: HashMap<String, String>) {
        pushNotification.showNotification(notification)
    }

    fun disconnectSocket() {
        mSocket.disconnect()
    }

    fun navigateChatRoom(activity: Activity, pos: Int) {
        val userModel: UserModel? = contacts.value?.get(pos)
        val data: Bundle = Bundle().apply {
            putString(Const.ROOM_ID, userModel?.roomId)
            putString(Const.ID_USER, userModel?.id)
            putString(Const.NAME_USER, userModel?.name)
            putString(Session.SOCKETID, userModel?.socketId)
        }
        Intent(activity, ChatRoom::class.java).apply {
            this.putExtra(DATA_USER, data)
            activity.startActivity(this)
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