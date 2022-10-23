package com.example.chatapp.viewModels.browser

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.App
import com.example.chatapp.helpers.Session
import com.example.chatapp.repositoryApi.RemoteDataProvider
import com.example.chatapp.repositoryApi.models.UserModel
import com.example.chatapp.repositoryApi.browser.IBrowserPresenter
import com.example.chatapp.repositoryApi.models.MessageModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.notifications.PushNotification
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BrowserViewModel(application: Application) : SocketEvent(application), IBrowserPresenter {
    @Inject
    lateinit var provider: RemoteDataProvider
    val listUserFound: MutableLiveData<MutableList<UserModel>> = MutableLiveData<MutableList<UserModel>>()
    private lateinit var userId: String
    private lateinit var userName: String
    private val TO: String = "toU"
    private val FROM: String = "fromU"
    private val NAME: String = "name"
    private val SOCKETID: String = "toSocketId"
    private val NOTIFICATION: String = "notification"
    private var context: Application = application
    private val pushNotification: PushNotification = PushNotification(application.applicationContext)


    init {
        (application as App).getComponent().inject(this)

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
                Log.e("error", t.message, t.cause)
            }

        })
    }


    override fun receiveMessage(message: MessageModel) {}

    override fun receiveNotifications(notification: HashMap<String, String>) {
        pushNotification.showNotification(notification)
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