package com.example.chatapp.remoteRepository

import android.util.Log
import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.NotificationResponse
import com.example.chatapp.remoteRepository.models.FriendEntity
import com.example.chatapp.remoteRepository.models.NewFriendEntity
import com.example.chatapp.remoteRepository.models.UserModel
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

    override fun getMessages(token: String, roomId: String, res: IResponseProvider){
        retrofit.create(ApiEndPoint::class.java).getMessage(roomId, token).enqueue(object : Callback<MutableList<MessageModel>> {
            override fun onResponse(call: Call<MutableList<MessageModel>>, response: Response<MutableList<MessageModel>>) {
                if(response.isSuccessful) {
                    response.body()?.let { list ->
                        res.response(list)
                    }
                } else {
                    res.response(mutableListOf<MutableList<MessageModel>>())
                }
            }

            override fun onFailure(call: Call<MutableList<MessageModel>>, t: Throwable) {
                Log.e("error", t.message, t.cause)
            }
        })
    }

    override suspend fun getUserChats(token: String) : Response<MutableList<UserModel>> {
        return withContext(Dispatchers.IO) {
            retrofit.create(ApiEndPoint::class.java).getUserChats(token).execute()
        }
    }

    override suspend fun login(userLogin: UserLogin): Response<AuthApiResponse> {
        return withContext(Dispatchers.IO) {
            retrofit.create(ApiEndPoint::class.java).login(user = userLogin).execute()
        }
    }

    override suspend fun signIn(newUser: HashMap<String, String>): Response<AuthApiResponse> {
       return withContext(Dispatchers.IO){
           retrofit.create(ApiEndPoint::class.java).registerUser(newUser).execute()
       }
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

    override suspend fun retrieveUserFriends(token: String): FriendEntity? {
        return try {
            withContext(Dispatchers.IO) {
                val res = retrofit.create(ApiEndPoint::class.java).getUserFriends(token).execute()
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