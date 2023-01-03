package com.example.chatapp.remoteRepository

import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.LoginResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse
import com.example.chatapp.remoteRepository.models.FriendEntity
import com.example.chatapp.remoteRepository.models.NewFriendEntity
import com.example.chatapp.viewModels.login.ErrorLogin
import com.example.chatapp.viewModels.login.Error
import com.example.chatapp.viewModels.login.IResponseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDataProvider @Inject constructor(private val retrofit: Retrofit): Repository {

    override fun getMessages(token: String, roomId: String): Call<MutableList<MessageModel>> {
        return retrofit.create(ApiEndPoint::class.java).getMessage(roomId, token)
    }

    override fun getUserContacts(token: String): Call<FriendEntity> {
        return retrofit.create(ApiEndPoint::class.java).getContacts(token)
    }

    override fun login(userLogin: UserLogin, res: IResponseProvider) {
        retrofit.create(ApiEndPoint::class.java).login(userLogin).enqueue(object: Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.OK == true) {
                    res.response(response.body())
                } else {
                    res.responseError(ErrorLogin(Error.USER_NOT_EXIST_ERROR))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                res.responseError(ErrorLogin(Error.NET_ERROR))
            }

        })
    }

    override fun signIn(newUser: HashMap<String, String>): Call<LoginResponse> {
       return retrofit.create(ApiEndPoint::class.java).registerUser(newUser)
    }

    override fun searchNewUser(token: String, value: String, res: IResponseProvider) {
        retrofit.create(ApiEndPoint::class.java).findNewUsers(token, value).enqueue(object : Callback<MutableList<NewFriendEntity>> {
            override fun onResponse(call: Call<MutableList<NewFriendEntity>>, response: Response<MutableList<NewFriendEntity>>) {
                if (response.isSuccessful) {
                    res.response(response.body())
                } else {
                    res.responseError(ErrorLogin(Error.NET_ERROR))
                }
            }

            override fun onFailure(call: Call<MutableList<NewFriendEntity>>, t: Throwable) {

            }

        })
    }

    override suspend fun getNotification(id: String): MutableList<NotificationModel>? {
        return try {
            withContext(Dispatchers.IO) {
                val res : Response<MutableList<NotificationModel>?> = retrofit.create(ApiEndPoint::class.java).getNotifications(id).execute()
                if (res.isSuccessful) {
                    res.body()
                } else {
                    mutableListOf<NotificationModel>()
                }
            }
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }

    override suspend fun acceptNotification(token: String, notification: NotificationModel): NotificationResponse? {
        return try {
            withContext(Dispatchers.IO) {
                val res = retrofit.create(ApiEndPoint::class.java).acceptNotification(token, notification).execute()
                if (res.isSuccessful) {
                    res.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }

    override suspend fun rejectNotification(token: String, notification: NotificationModel): NotificationResponse? {
        return try {
            withContext(Dispatchers.IO) {
                val res = retrofit.create(ApiEndPoint::class.java).rejectNotification(notification).execute()
                if (res.isSuccessful) {
                    res.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            throw e.fillInStackTrace()
        }
    }
}