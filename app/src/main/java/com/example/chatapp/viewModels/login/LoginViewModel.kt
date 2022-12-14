package com.example.chatapp.viewModels.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.chatapp.App
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.LoginResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.remoteRepository.models.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel(application: Application): AndroidViewModel(application), ILogin {

    @Inject
    lateinit var modelProvider: RemoteDataProvider
    val user: MutableLiveData<UserModel> = MutableLiveData<UserModel>()
    val error: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val regexEmail: String = "^[A-Za-z0-9]+@([a-zA-Z]+)(.)[a-zA-Z]{1,3}$"

    init {
        (application as App).getComponent().inject(this)
    }

    override fun login (userLogin: UserLogin) {

        modelProvider.login(userLogin).enqueue(object: Callback<LoginResponse> {

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

    override fun singin(newUser: HashMap<String, String>) {
        modelProvider.signIn(newUser).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.OK == true) {
                    user.postValue(response.body()?.body)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

        })
    }

    fun registerUser(newUser: HashMap<String, String>) {
        newUser["email"]?.let {
            if (validateEmail(it)) {
                singin(newUser)
            }
        }
    }

    private fun validatePassword(pw: String, confirmPw: String): Boolean {
        return (pw == confirmPw)
    }

    private fun validateEmail(mail: String): Boolean {
        return regexEmail.toRegex().matches(mail)
    }

}