package com.example.chatapp.views.ui.login


import com.example.chatapp.remoteRepository.models.ApiError


interface IBaseAuthView {

    fun navigateToHome()
    fun goBack()
    fun showError(error: ApiError)
    fun showError()
}