package com.example.chatapp.api

import com.example.chatapp.remoteRepository.models.FriendEntity
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.NewFriendEntity
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse
import com.example.chatapp.remoteRepository.models.NotificationResponse
import com.example.chatapp.remoteRepository.models.UserModel
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path

interface ApiEndPoint {

    //GET
    @GET("notifications")
    fun getNotifications(@Header("Authorization") auth: String): Call<MutableList<NotificationModel>?>

    @GET("contacts")
    fun getUserChats(@Header("Authorization") auth: String): Call<MutableList<UserModel>>

    @GET("friends")
    fun getUserFriends(@Header("Authorization") auth: String): Call<FriendEntity>

    @GET("messages/{roomId}")
    fun getMessage(@Header("Authorization") auth: String, @Path("roomId") roomId: String): Call<MutableList<MessageModel>>

    @GET("chat/{user}")
    fun findNewUsers(@Header("Authorization") auth: String, @Path("user") user: String): Call<MutableList<NewFriendEntity>>

    //POST
    @Headers("Content-type: application/json")
    @POST("login")
    fun login(@Body user: UserLogin): Call<AuthApiResponse>

    @Headers("Content-type: application/json")
    @POST("signup")
    fun registerUser(@Body user: HashMap<String, String>): Call<AuthApiResponse>

    @Headers("Content-type: application/json")
    @POST("acceptNotification")
    fun acceptNotification(@Header("Authorization") auth: String, @Body notificationModel: NotificationModel): Call<NotificationResponse?>

    @Headers("Content-type: application/json")
    @POST("rejectNotification")
    fun rejectNotification(@Body notificationModel: NotificationModel): Call<NotificationResponse?>

    @Headers("Content-type: application/json")
    @POST("request")
    fun sendRequest(@Body user: HashMap<String, String>): Call<Boolean>


}