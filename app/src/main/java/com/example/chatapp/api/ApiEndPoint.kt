package com.example.chatapp.api

import com.example.chatapp.repositoryApi.models.UserModel
import com.example.chatapp.repositoryApi.chat.MessageModel
import com.example.chatapp.repositoryApi.login.LoginResponse
import com.example.chatapp.repositoryApi.login.UserLogin
import com.example.chatapp.repositoryApi.models.NotificationModel
import com.example.chatapp.repositoryApi.models.NotificationResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    //GET
    @GET("notifications/{id}")
    fun getNotifications(@Path("id") id: String): Call<MutableList<NotificationModel>?>

    @GET("contacts/{id}")
    fun getContacts(@Path("id") id: String): Call<MutableList<UserModel>>

    @GET("messages/{roomId}")
    fun getMessage(@Path("roomId") roomId: String): Call<MutableList<MessageModel>>

    @GET("chat/{user}")
    fun finNewUsers(@Path("user") user: String): Call<MutableList<UserModel>>

    //POST
    @Headers("Content-type: application/json")
    @POST("login")
    fun login(@Body user: UserLogin): Call<LoginResponse>

    @Headers("Content-type: application/json")
    @POST("signin")
    fun registerUser(@Body user: HashMap<String, String>): Call<LoginResponse>

    @Headers("Content-type: application/json")
    @POST("acceptNotification")
    fun acceptNotification(@Body notificationModel: NotificationModel): Call<NotificationResponse?>

    @Headers("Content-type: application/json")
    @POST("rejectNotification")
    fun rejectNotification(@Body notificationModel: NotificationModel): Call<NotificationResponse?>

    @Headers("Content-type: application/json")
    @POST("request")
    fun sendRequest(@Body user: HashMap<String, String>): Call<Boolean>


}