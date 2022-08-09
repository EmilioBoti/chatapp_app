package com.example.chatapp

import com.example.chatapp.model.UserModel
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
}