package com.example.chatapp.model.home

import com.example.chatapp.model.UserModel
import retrofit2.Call

interface IHomeModel {
    fun getUserContacts(currentUser: String): Call<MutableList<UserModel>>
}