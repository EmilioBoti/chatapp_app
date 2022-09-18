package com.example.chatapp.repositoryApi.login

import com.example.chatapp.api.ApiEndPoint
import com.example.chatapp.helpers.utils.Utils
import retrofit2.Call

class LoginProvider: LoginModel {


    override fun login(userLogin: UserLogin): Call<LoginResponse> {

        val service = Utils.getRetrofitBuilder()
            .create(ApiEndPoint::class.java)
        return service.login(userLogin)
    }

    override fun signin(newUser: HashMap<String, String>): Call<LoginResponse> {
        return Utils.getRetrofitBuilder()
                .create(ApiEndPoint::class.java)
                .registerUser(newUser)
    }
}