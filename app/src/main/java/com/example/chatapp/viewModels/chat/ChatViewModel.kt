package com.example.chatapp.viewModels.chat

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.App
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.convertToMessageEntity
import com.example.chatapp.repositoryLocal.database.AppDataBase
import com.example.chatapp.repositoryLocal.database.entity.convertToMessageModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.network.NetConnectivity
import com.example.chatapp.viewModels.network.State
import com.example.chatapp.viewModels.notifications.PushNotification
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ChatViewModel(application: Application): SocketEvent(application), IChat.Presenter {
    @Inject
    lateinit var chatProvider: RemoteDataProvider
    @Inject
    lateinit var db: AppDataBase
    val listMessages: MutableLiveData<MutableList<MessageModel>> = MutableLiveData<MutableList<MessageModel>>()
    private var bundle: Bundle? = null
    private var currentUser: Map<String, *>? = null
    private val pushNotification: PushNotification = PushNotification(application.applicationContext)
    lateinit var currentUserId: String

    init {
        (application as App).getComponent().inject(this)
        currentUser = Session.getSession(application.applicationContext)
        mSocket.on("disconnect") {}
    }

    override fun setUp(bundle: Bundle?) {
        this.bundle = bundle
        currentUser?.let { currentUserId = it[Const.ID_USER].toString() }
        checkConnectivity()
    }

    override fun getMessages() {
        bundle?.getString(Const.ROOM_ID)?.let {roomId ->
            chatProvider.getMessages(roomId).enqueue(object : Callback<MutableList<MessageModel>> {
                override fun onResponse(call: Call<MutableList<MessageModel>>, response: Response<MutableList<MessageModel>>) {
                    if(response.isSuccessful) {
                        listMessages.postValue(response.body())
                        viewModelScope.launch {
                            db.getChatDao().insertAllMessage(response.body()?.map { it.convertToMessageEntity()} as MutableList)
                        }
                    }
                }

                override fun onFailure(call: Call<MutableList<MessageModel>>, t: Throwable) {
                    getContactMessage()
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

    private fun checkConnectivity() {
        connectivityState.setUpListener(object : NetConnectivity {
            override fun network(state: State) {
                when(state) {
                    State.AVAILABLE -> getMessages()
                    State.UNAVAILABLE -> getContactMessage()
                }
            }
        })
    }

    override fun getContactMessage() {
        bundle?.getString(Const.ROOM_ID)?.let { roomId ->
            viewModelScope.launch {
                val list = db.getChatDao().getRoomMessages(roomId).map { it.convertToMessageModel() }
                listMessages.postValue(list as MutableList)
            }
        }
    }

    override fun receiveMessage(message: MessageModel) {
        if (isRoomUser(message.roomId)) {
            listMessages.value?.add(message)
            listMessages.postValue(listMessages.value)
        }
        viewModelScope.launch {
            db.getChatDao().insertMessage(message.convertToMessageEntity())
        }
    }

    override fun receiveNotifications(notification: HashMap<String, String>) {
        pushNotification.showNotification(notification)
    }

    private fun isRoomUser(roomId: String): Boolean {
        return roomId == bundle?.getString(Const.ROOM_ID)
    }

}