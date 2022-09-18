package com.example.chatapp.repositoryApi.browser

import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.repositoryApi.models.UserModel
import retrofit2.Call

class BrowserProvider: IBrowserModel {

    override fun searchNewUser(value: String): Call<MutableList<UserModel>> {
        val retrofit = Utils.getRetrofitBuilder()
        return retrofit.create(ApiEndPoint::class.java).finNewUsers(value)
    }
}