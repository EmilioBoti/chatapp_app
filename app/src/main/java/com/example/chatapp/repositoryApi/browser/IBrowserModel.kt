package com.example.chatapp.repositoryApi.browser

import com.example.chatapp.repositoryApi.models.UserModel
import retrofit2.Call

interface IBrowserModel {
    fun searchNewUser(value: String) : Call<MutableList<UserModel>>
}