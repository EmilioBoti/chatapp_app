package com.example.chatapp.viewModels.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.views.home.HomeActivity
import com.example.chatapp.views.ui.login.LoginFragment
import javax.inject.Inject

class BasePresenter @Inject constructor() : IBasePresenter {
    private lateinit var viewPresenter: IBaseViewPresenter
    private lateinit var context: Context
    private lateinit var token: String


    override fun setUp(viewPresenter: IBaseViewPresenter) {
        this.viewPresenter = viewPresenter
        this.context = viewPresenter.getBaseActivity().applicationContext
        isUserLogin()
    }

    override fun isUserLogin() {
        if (isLogin() && setSocket(getUserToken())) {
            navigateToHome<HomeActivity>(HomeActivity())
        } else {
            navigateToLogin()
        }
    }

    override fun setSocket(token: String): Boolean {
        return if (token.isNotEmpty()) {
            SocketCon.setSocket(token)
            true
        } else {
            false
        }
    }

    override fun <T> navigateToHome(screen: T) {
        viewPresenter.navigateToHome(screen)
    }

    override fun navigateToLogin() {
        viewPresenter.navigateToLogin(LoginFragment())
    }


    private fun isLogin(): Boolean {
        return Session.isLogin(context) == true
    }

    private fun getUserToken(): String {
        var token: String = ""
        Session.getToken(context)?.let { token = it }
        return token
    }

}