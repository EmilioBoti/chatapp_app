package com.example.chatapp.viewModels.login

import com.example.chatapp.helpers.enums.Navigation
import com.example.chatapp.remoteRepository.models.ApiError
import okhttp3.ResponseBody


interface IBaseAuthViewModel {

    fun navigateTo(navigation: Navigation)

    fun showAuthError(error: ApiError)

    fun parseApiError(error: ResponseBody?) : ApiError

    fun checkError(error: Exception)

}