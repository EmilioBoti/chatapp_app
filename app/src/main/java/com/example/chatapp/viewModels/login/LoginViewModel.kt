package com.example.chatapp.viewModels.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.login.LoginProvider
import com.example.chatapp.model.login.LoginResponse
import com.example.chatapp.model.login.UserLogin
import com.example.chatapp.model.UserModel
import retrofit2.Call
import retrofit2.Response

class LoginViewModel: ViewModel(), ILogin {

    private lateinit var modelProvider: LoginProvider
    val user: MutableLiveData<UserModel> = MutableLiveData<UserModel>()
    val error: MutableLiveData<Boolean> = MutableLiveData<Boolean>()


    override fun login (userLogin: UserLogin) {
        modelProvider = LoginProvider()

        modelProvider.login(userLogin).enqueue(object: retrofit2.Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.OK == true) {
                    user.postValue(response.body()?.body)
                } else error.postValue(false)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                error.postValue(false)
            }

        })

    }

}