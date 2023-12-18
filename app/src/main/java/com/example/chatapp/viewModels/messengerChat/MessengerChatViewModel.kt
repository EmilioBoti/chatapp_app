package com.example.chatapp.viewModels.messengerChat

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.convertToMessageEntity
import com.example.chatapp.service.MessageService
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.messengerChat.useCase.IChatUseCase
import com.example.chatapp.viewModels.network.State
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class MessengerChatViewModel(private val chatProvider: IChatUseCase): SocketEvent(),
    IMessengerChat.Presenter, MessageService.IMessengerEvent {

    val listMessages: MutableLiveData<MutableList<MessageModel>> = MutableLiveData<MutableList<MessageModel>>()
    private var bundle: Bundle? = null
    private var currentUser: Map<String, *>? = null
    lateinit var currentUserId: String

    init {
        currentUser = Session.getSession()
    }

    companion object {
        fun provideFactory(chatProvider: IChatUseCase): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {

                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return MessengerChatViewModel(chatProvider) as T
                }
            }
        }

    }

    override fun setUp(bundle: Bundle?) {
        this.bundle = bundle
        this.bundle?.let { currentUserId =  it[Const.ID_USER].toString() }
        getMessages()
    }

    fun initBindService(context: Context) {
        context.bindService(Intent(context, MessageService::class.java), object : ServiceConnection {

            override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
                val messageService: MessageService = (service as MessageService.LocalBinder).getService()
                messageService.init(mSocket, this@MessengerChatViewModel)
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.e("service", "service disconnected")
            }

        }, BIND_AUTO_CREATE)
    }

    override fun getMessages() {
        bundle?.getString(Const.ROOM_ID)?.let { roomId ->

            viewModelScope.launch {
                try {
                    val response = chatProvider.getMessages(roomId, token)

                    if (response.isSuccessful) {
                        response.body()?.let {
                            saveAllSmsLocal(it)
                            listMessages.postValue(it)
                        }
                    } else {
                        // not api error method implemented yet.
                    }
                } catch (e: Exception) {
                    throw e // not applied, change yet
                }
            }
        }
    }

    private suspend fun saveAllSmsLocal(list: MutableList<MessageModel>) {
        chatProvider.saveAllSmsLocal(list.map { it.convertToMessageEntity()} as MutableList)
    }

    override fun sendMessage(text: String) {
        val messagedId: String = UUID.randomUUID().toString()
        this.bundle?.let {
            currentUser?.let { currentUser ->
                val map: HashMap<String, String?> = HashMap<String, String?>()
                map[Const.ROOM_ID] = it.getString(Const.ROOM_ID)
                map[Const.FROM_USER] = currentUser["id"].toString()
                map[Const.TO_USER] = it.getString(Const.ID_USER)
                map[Const.NAME_USER] = it[Const.NAME_USER].toString()
                map[Const.MESSAGE] = text
                map[Const.MESSAGE_ID] = messagedId
                mSocket.emit(Const.PRIVATE_SMS, Gson().toJson(map))
            }
        }
    }

    private fun sendingMessage(map: HashMap<String, String?>) {
        val date = Timestamp(System.currentTimeMillis())
        map[Const.DATE] = date.toString()
        val sms = MessageModel(
            map[Const.MESSAGE_ID].toString(),
            map[Const.ROOM_ID].toString(),
            map[Const.FROM_USER].toString(),
            map[Const.TO_USER].toString(),
            map[Const.NAME_USER].toString(),
            map[Const.MESSAGE].toString(),
            map[Const.DATE].toString())

        mSocket.emit(Const.PRIVATE_SMS, Gson().toJson(map))
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("HH:mm.a", Locale.getDefault())
        return format.format(date).lowercase()
    }

    override fun getContactMessage() {
        bundle?.getString(Const.ROOM_ID)?.let { roomId ->
            viewModelScope.launch {
                val list = chatProvider.getLocalMessage(roomId)
                listMessages.postValue(list)
            }
        }
    }

    override fun receiveMessage(message: MessageModel) {
        if (isRoomUser(message.roomId)) {
            listMessages.value?.add(message)
            listMessages.postValue(listMessages.value)
        }
        viewModelScope.launch {
            chatProvider.saveMessageLocal(message.convertToMessageEntity())
        }
    }

    override fun receiveNotifications(notification: HashMap<String, String>) {}

    override fun isConnectivityAvailable(state: State) {}

    private fun isRoomUser(roomId: String): Boolean {
        return roomId == bundle?.getString(Const.ROOM_ID)
    }

    override fun onReceiveNewMessage(message: MessageModel) {
        Log.e("newMessage", message.message)
        if (isRoomUser(message.roomId)) {
            listMessages.value?.add(message)
            listMessages.postValue(listMessages.value)
        }
        viewModelScope.launch {
            chatProvider.saveMessageLocal(message.convertToMessageEntity())
        }
    }

}