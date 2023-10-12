package com.example.chatapp.viewModels.home

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.App
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.FriendEntity
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.convertToUserEntity
import com.example.chatapp.repositoryLocal.database.AppDataBase
import com.example.chatapp.repositoryLocal.database.entity.convertToUserModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.home.useCase.IHomeUseCase
import com.example.chatapp.viewModels.login.ErrorLogin
import com.example.chatapp.viewModels.login.IResponseProvider
import com.example.chatapp.viewModels.network.NetConnectivity
import com.example.chatapp.viewModels.network.State
import com.example.chatapp.viewModels.notifications.PushNotification
import com.example.chatapp.views.home.BaseActivity
import com.example.chatapp.views.ui.chatRoom.ChatRoom
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel(private val provider: IHomeUseCase,
                    application: Application): SocketEvent(), IHomeViewModel {

    private val _contacts: MutableLiveData<MutableList<UserModel>> = MutableLiveData<MutableList<UserModel>>()
    private lateinit var friendContacts: MutableList<UserModel>
    val contacts: LiveData<MutableList<UserModel>> = _contacts
    private var currentUser: String? = null
    private val pushNotification: PushNotification = PushNotification(application.applicationContext)

    companion object {
        private const val DATA_USER: String = "data"
    }

    init {
        pushNotification.notificationChannel()
        pushNotification.smsNotificationChannel()
        setUp()
    }

    private fun setUp() {
        if(!mSocket.connected()) mSocket.connect()
    }

    /**
     * Deprecated method, not in use
     */
    override fun updateSocket(id: String) {
        if(!mSocket.connected()) mSocket.connect()

        mSocket.on("connect") {
            val map = HashMap<String, String>()
            map[Session.ID] = id
            map[Session.SOCKETID] = mSocket.id()

            mSocket.emit("user", Gson().toJson(map))
        }
    }

    fun findYourFriends(name: String) {
        if (name.isEmpty()) {
            _contacts.value?.let { updateListChats(friendContacts) }
        } else {
            updateListChats(getFriendFound(name))
        }
    }

    private fun getFriendFound(name: String) : MutableList<UserModel> {
        return _contacts.value?.filter { it.name.lowercase().contains(name.lowercase()) } as MutableList<UserModel>
    }

    override fun getContacts() {
        provider.getUserContact(token, object : IResponseProvider {
            override fun <T> response(data: T) {
                val users = data as MutableList<UserModel>
                friendContacts = users
                updateListChats(users)
                updateAllUsers(users)
            }

            override fun responseError(err: ErrorLogin) {

            }

        })
    }

    private fun updateAllUsers(users: MutableList<UserModel>) {
        val list = users.map { it.convertToUserEntity() }
        viewModelScope.launch {
            provider.updateAllUsers(list as MutableList)
        }
    }

    override fun getLocalContacts() {
        viewModelScope.launch {
            val id = Session.getSession()?.get(Session.ID)

            id?.let {
                val list = provider.getUserContactLocal(it as String)
                _contacts.postValue(list)
            }
        }
    }

    override fun receiveMessage(message: MessageModel) {
        if (currentUser != message.fromU) { pushNotification.showSmsNotification(message) }
        contacts.value?.forEach {
            if (it.id == message.fromU) {
                it.lastMessage = message.message
                it.times = message.times
            }
        }
        contacts.value?.let { updateListChats(it) }
    }

    override fun receiveNotifications(notification: HashMap<String, String>) {
        pushNotification.showNotification(notification)
    }

    override fun isConnectivityAvailable(state: State) {
        if (state == State.AVAILABLE) {
            getContacts()
        } else if (state == State.UNAVAILABLE) {
            getLocalContacts()
        }
    }

    private fun updateListChats(chats: MutableList<UserModel>) {
        chats.sort()
        _contacts.postValue(chats)
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