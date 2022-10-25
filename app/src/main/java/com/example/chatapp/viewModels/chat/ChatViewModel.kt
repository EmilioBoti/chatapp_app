package com.example.chatapp.viewModels.chat

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.App
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.notifications.PushNotification
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ChatViewModel(application: Application): SocketEvent(application), IChat.Presenter {
    @Inject
    lateinit var chatProvider: RemoteDataProvider
    val listMessages: MutableLiveData<MutableList<MessageModel>> = MutableLiveData<MutableList<MessageModel>>()
    private var bundle: Bundle? = null
    private var currentUser: Map<String, *>? = null
    private val pushNotification: PushNotification = PushNotification(application.applicationContext)

    init {
        (application as App).getComponent().inject(this)
        currentUser = Session.getSession(application.applicationContext)
        mSocket.on("disconnect") {}
    }

    override fun getMessages(bundle: Bundle?) {
        this.bundle = bundle

        bundle?.getString(Const.ROOM_ID)?.let {
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
                map[Const.ROOM_ID] = it.getString(Const.ROOM_ID)
                map[Const.FROM_USER] = currentUser["id"].toString()
                map[Const.TO_USER] = it.getString(Const.ID_USER)
                map[Const.NAME_USER] = it[Const.NAME_USER].toString()
                map[Const.MESSAGE] = text

                mSocket.emit(Const.PRIVATE_SMS, Gson().toJson(map))
            }
        }
    }

    override fun receiveMessage(message: MessageModel) {
        if (isRoomUser(message.roomId)) {
            listMessages.value?.add(message)
            listMessages.postValue(listMessages.value)
        }
    }

    override fun receiveNotifications(notification: HashMap<String, String>) {
        pushNotification.showNotification(notification)
    }

    private fun isRoomUser(roomId: String): Boolean {
        return roomId == bundle?.getString(Const.ROOM_ID)
    }

}