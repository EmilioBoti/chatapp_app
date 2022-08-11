package com.example.chatapp.model.login

import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.helpers.utils.Utils
import retrofit2.Call

class LoginProvider: LoginModel {


    override fun login(userLogin: UserLogin): Call<LoginResponse> {

        val retrofit = Utils.getRetrofitBuilder()

        val service = retrofit.create(ApiEndPoint::class.java)
        return service.login(userLogin)
    }
}