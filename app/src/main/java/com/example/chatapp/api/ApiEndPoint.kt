package com.example.chatapp.api

import com.example.chatapp.model.UserModel
import com.example.chatapp.model.chat.MessageModel
import com.example.chatapp.model.login.LoginResponse
import com.example.chatapp.model.login.UserLogin
import retrofit2.Call
import retrofit2.http.*

interface ApiEndPoint {

    @Headers("Content-type: application/json")
    @POST("login")
    fun login(@Body user: UserLogin): Call<LoginResponse>

    @GET("contacts/{id}")
    fun getContacts(@Path("id") id: String): Call<MutableList<UserModel>>

    @GET("messages/{roomId}")
    fun getMessage(@Path("roomId") roomId: String): Call<MutableList<MessageModel>>
}