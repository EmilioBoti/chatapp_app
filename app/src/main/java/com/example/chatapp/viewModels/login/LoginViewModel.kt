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
    val error: MutableLiveData<ErrorLogin> = MutableLiveData<ErrorLogin>()
    private val regexEmail: String = "^[A-Za-z0-9]+@([a-zA-Z]+)(.)[a-zA-Z]{2,3}$"
    private val lengthPw: Int = 5

    init {
        (application as App).getComponent().inject(this)
    }

    override fun login (userLogin: UserLogin) {

        modelProvider.login(userLogin).enqueue(object: Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.OK == true) {
                    user.postValue(response.body()?.body)
                } else {
                    error.postValue(ErrorLogin(Error.USER_NOT_EXIST_ERROR))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                error.postValue(ErrorLogin(Error.NET_ERROR))
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

    fun validateInputs(userLogin: UserLogin) {
        if (!validateEmail(userLogin.email)) {
            error.postValue(ErrorLogin(Error.EMAIL_ERROR))
        } else if (!validatePassword(userLogin.pw)) {
            error.postValue(ErrorLogin(Error.PASSWORD_ERROR))
        } else {
            login(userLogin)
        }
    }

    fun registerUser(newUser: HashMap<String, String>) {
        newUser["email"]?.let {
            if (validateEmail(it.trim())) {
                singin(newUser)
            }
        }
    }

    private fun validateEmail(mail: String): Boolean {
        return regexEmail.toRegex().matches(mail)
    }

    private fun validatePassword(pw: String): Boolean {
        return pw.length >= lengthPw
    }

}