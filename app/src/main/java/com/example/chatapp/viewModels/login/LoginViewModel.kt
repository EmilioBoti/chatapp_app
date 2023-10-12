package com.example.chatapp.viewModels.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatapp.helpers.enums.Navigation
import com.example.chatapp.remoteRepository.models.ApiError
import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.useCases.IAuthUseCase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception


class LoginViewModel(private val modelProvider: IAuthUseCase,
                     private val presenter: IAuthPresenter): BaseAuthViewModel(), ILogin {


    val user: MutableLiveData<String> = MutableLiveData<String>()
    private val regexEmail: String = "^[A-Za-z0-9]+@([a-zA-Z]+)(.)[a-zA-Z]{2,3}$"
    private val lengthPw: Int = 5


    override fun login(userLogin: UserLogin) {
        viewModelScope.launch{
            try {
                val resp = modelProvider.login(userLogin)
                if (resp.isSuccessful) {
                    saveSession(resp.body())
                } else {
                    showAuthError(parseApiError(resp.errorBody()))
                }
            } catch (e: Exception) {
                checkError(e)
            }
        }
    }

    override fun singin(newUser: HashMap<String, String>) {
        modelProvider.signIn(newUser).enqueue(object : Callback<AuthApiResponse> {
            override fun onResponse(call: Call<AuthApiResponse>, response: Response<AuthApiResponse>) {
                if (response.isSuccessful) {
                    saveSession(response.body())
                }
            }

            override fun onFailure(call: Call<AuthApiResponse>, t: Throwable) {

            }

        })
    }

    private fun saveSession(response: AuthApiResponse?) {
        response?.let {
            it.token.let { token ->
                presenter.saveToken(token)
                presenter.setUpSocket(token)
                navigateTo(Navigation.MAIN)
            }
        }
    }

    fun validateInputs(userLogin: UserLogin) {
        if (!validateEmail(userLogin.email)) {
            errorAuth.postValue(
                ApiError(
                    "email",
                    "the email is incorrect.",
                    400))
        } else if (!validatePassword(userLogin.pw)) {
            errorAuth.postValue(ApiError(
                "password",
                "the password is incorrect.",
                400))
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