package com.example.chatapp.repositoryApi.home

import com.example.chatapp.repositoryApi.models.UserModel
import retrofit2.Call

interface IHomeModel {
    fun getUserContacts(currentUser: String): Call<MutableList<UserModel>>
}