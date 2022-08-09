package com.example.chatapp.model.home

import com.example.chatapp.ApiEndPoint
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.model.UserModel
import retrofit2.Call

class HomeProvider: IHomeModel {

    override fun getUserContacts(currentUser: String): Call<MutableList<UserModel>> {
        val retrofit = Utils.getRetrofitBuilder()
        return retrofit.create(ApiEndPoint::class.java).getContacts(currentUser)
    }
}