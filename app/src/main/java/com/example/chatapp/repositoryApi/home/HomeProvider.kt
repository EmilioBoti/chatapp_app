package com.example.chatapp.repositoryApi.home

import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.repositoryApi.models.UserModel
import retrofit2.Call

class HomeProvider: IHomeModel {

    override fun getUserContacts(currentUser: String): Call<MutableList<UserModel>> {
        val retrofit = Utils.getRetrofitBuilder()
        return retrofit.create(ApiEndPoint::class.java).getContacts(currentUser)
    }
}