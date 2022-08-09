package com.example.chatapp.model.login

import com.example.chatapp.ApiEndPoint
import com.example.chatapp.helpers.utils.Consts
import com.example.chatapp.helpers.utils.Utils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginProvider: LoginModel {


    override fun login(userLogin: UserLogin): Call<LoginResponse> {

        val retrofit = Utils.getRetrofitBuilder()

        val service = retrofit.create(ApiEndPoint::class.java)
        return service.login(userLogin)
    }
}