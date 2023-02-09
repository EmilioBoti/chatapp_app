package com.example.chatapp.api

import com.example.chatapp.remoteRepository.models.FriendEntity
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.NewFriendEntity
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.LoginResponse
import com.example.chatapp.remoteRepository.models.NotificationResponse
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
    fun getContacts(@Header("Authorization") auth: String): Call<FriendEntity>

    @GET("messages/{roomId}")
    fun getMessage(@Header("Authorization") auth: String, @Path("roomId") roomId: String): Call<MutableList<MessageModel>>

    @GET("chat/{user}")
    fun findNewUsers(@Header("Authorization") auth: String, @Path("user") user: String): Call<MutableList<NewFriendEntity>>

    //POST
    @Headers("Content-type: application/json")
    @POST("login")
    fun login(@Body user: UserLogin): Call<LoginResponse>

    @Headers("Content-type: application/json")
    @POST("signin")
    fun registerUser(@Body user: HashMap<String, String>): Call<LoginResponse>

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