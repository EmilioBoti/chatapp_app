package com.example.chatapp.viewModels.home

import android.content.Context

interface IBasePresenter {
    fun setUp(viewPresenter: IBaseViewPresenter)
    fun isUserLogin()
    fun setSocket(token: String): Boolean
    fun <T> navigateToHome(screen: T)
    fun navigateToLogin()
}