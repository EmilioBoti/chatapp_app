package com.example.chatapp.views.ui.login.welcome

interface IBasePresenter {
    fun setUp(viewPresenter: IBaseViewPresenter)
    fun isUserLogin()
    fun setSocket(token: String): Boolean
    fun <T> navigateToHome(screen: T)
    fun navigateToLogin()
}