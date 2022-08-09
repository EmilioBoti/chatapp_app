package com.example.chatapp.model.login

import retrofit2.Call

interface LoginModel {
    fun login(userLogin: UserLogin): Call<LoginResponse>
}