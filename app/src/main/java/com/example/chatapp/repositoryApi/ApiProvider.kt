package com.example.chatapp.repositoryApi

import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.repositoryApi.models.MessageModel
import com.example.chatapp.repositoryApi.login.LoginResponse
import com.example.chatapp.repositoryApi.login.UserLogin
import com.example.chatapp.repositoryApi.models.NotificationModel
import com.example.chatapp.repositoryApi.models.NotificationResponse
import com.example.chatapp.repositoryApi.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit

class ApiProvider: Repository {
    private var retrofit: Retrofit = Utils.getRetrofitBuilder()

    override fun getMessages(roomId: String): Call<MutableList<MessageModel>> {
        return retrofit.create(ApiEndPoint::class.java).getMessage(roomId)
    }

    override fun getUserContacts(currentUser: String): Call<MutableList<UserModel>> {
        return retrofit.create(ApiEndPoint::class.java).getContacts(currentUser)
    }

    override fun login(userLogin: UserLogin): Call<LoginResponse> {
        return retrofit.create(ApiEndPoint::class.java).login(userLogin)
    }

    override fun signIn(newUser: HashMap<String, String>): Call<LoginResponse> {
       return retrofit.create(ApiEndPoint::class.java).registerUser(newUser)
    }

    override fun searchNewUser(value: String): Call<MutableList<UserModel>> {
       return retrofit.create(ApiEndPoint::class.java).finNewUsers(value)
    }

    override suspend fun getNotification(id: String): MutableList<NotificationModel>? {
        return try {
            withContext(Dispatchers.IO) {
                retrofit.create(ApiEndPoint::class.java).getNotifications(id).execute().body()
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun acceptNotification(notification: NotificationModel): NotificationResponse? {
        return try {
            withContext(Dispatchers.IO) {
                retrofit.create(ApiEndPoint::class.java).acceptNotification(notification).execute().body()
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun rejectNotification(notification: NotificationModel): NotificationResponse? {
        return try {
            withContext(Dispatchers.IO) {
                retrofit.create(ApiEndPoint::class.java).rejectNotification(notification).execute().body()
            }
        } catch (e: Exception) {
            null
        }
    }
}