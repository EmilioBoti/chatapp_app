package com.example.chatapp.repositoryApi.login

import retrofit2.Call

interface LoginModel {
    fun login(userLogin: UserLogin): Call<LoginResponse>
    fun signin(newUser: HashMap<String, String>): Call<LoginResponse>
}