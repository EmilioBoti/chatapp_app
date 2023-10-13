package com.example.chatapp.views.ui.login.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.chatapp.App
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.enums.Navigation
import com.example.chatapp.remoteRepository.models.ApiError
import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.useCases.IAuthUseCase
import com.example.chatapp.views.ui.login.BaseAuthViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception


class LoginViewModel(private val modelProvider: IAuthUseCase): BaseAuthViewModel(), ILogin {


    val user: MutableLiveData<String> = MutableLiveData<String>()
    private val regexEmail: String = "^[A-Za-z0-9]+@([a-zA-Z]+)(.)[a-zA-Z]{2,3}$"
    private val lengthPw: Int = 5


    companion object {
        fun provideFactory(modelProvider: IAuthUseCase) : ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return LoginViewModel(modelProvider) as T
                }
            }
        }

    }

    override fun signingIn(userLogin: UserLogin) {
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

    override fun singingUp(newUser: HashMap<String, String>) {
        viewModelScope.launch {
            try {
                val result = modelProvider.signUp(newUser)
                if(result.isSuccessful) {
                    saveSession(result.body())
                } else {
                    showAuthError(parseApiError(result.errorBody()))
                }
            } catch (e: Exception) {
                checkError(e)
            }
        }
    }

    private fun saveSession(response: AuthApiResponse?) {
        response?.let {
            Session.saveUserSession(it)
            App.setUpSocketServer(it.token)
            navigateTo(Navigation.MAIN)
        }
    }

    override fun performSignIn(email: String, pw: String) {
        if (!validateEmail(email)) {
            errorAuth.postValue(
                ApiError(
                    "email",
                    "the email is incorrect.",
                    400))
        } else if (!validatePassword(pw)) {
            errorAuth.postValue(ApiError(
                "password",
                "the password is incorrect.",
                400))
        } else {
            signingIn(UserLogin(email,pw))
        }
    }

    override fun performSignUp(name: String, email: String, pw: String) {
        if (validateEmail(email.trim())) {
            singingUp(newUserToSend(name, email, pw))
        } else {
            showAuthError(ApiError(
                "email",
                "the email is incorrect.",
                400)
            )
        }
    }

    private fun newUserToSend(name: String, email: String, pw: String): HashMap<String, String> {
        return object : HashMap<String, String>() {
            init {
                put("name", name)
                put("email",email)
                put("pw", pw)
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