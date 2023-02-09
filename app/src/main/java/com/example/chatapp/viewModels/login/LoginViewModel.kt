package com.example.chatapp.viewModels.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.remoteRepository.models.LoginResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.useCases.IAuthUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val modelProvider: IAuthUseCase,
                     private val presenter: IAuthPresenter): ViewModel(), ILogin {


    val user: MutableLiveData<String> = MutableLiveData<String>()
    val error: MutableLiveData<ErrorLogin> = MutableLiveData<ErrorLogin>()
    private val regexEmail: String = "^[A-Za-z0-9]+@([a-zA-Z]+)(.)[a-zA-Z]{2,3}$"
    private val lengthPw: Int = 5


    override fun login (userLogin: UserLogin) {

        modelProvider.login(userLogin, object : IResponseProvider {
            override fun <T> response(data: T) {
                val r = data as LoginResponse
                saveSession(r)
            }

            override fun responseError(err: ErrorLogin) {
                error.postValue(err)
            }

        })
    }

    override fun singin(newUser: HashMap<String, String>) {
        modelProvider.signIn(newUser).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.OK == true) {
                    saveSession(response.body())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

        })
    }

    private fun saveSession(response: LoginResponse?) {
        response?.let {
            it.user?.let { user ->
                presenter.saveSession(user)
            }
            it.token?.let { token ->
                presenter.saveToken(token)
                presenter.setUpSocket(token)
                user.postValue(token)
            }
        }
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

    fun validateEmail(mail: String): Boolean {
        return regexEmail.toRegex().matches(mail)
    }

    fun validatePassword(pw: String): Boolean {
        return pw.length >= lengthPw
    }

}