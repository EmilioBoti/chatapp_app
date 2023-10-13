package com.example.chatapp.views.ui.login.welcome

import android.app.Activity
import androidx.fragment.app.Fragment

/**
 * this interface is more for a future...
 */
interface IBaseViewPresenter {
    fun getBaseActivity(): Activity
    fun navigateToLogin(fragment: Fragment)
    fun <T> navigateToHome(screen: T)
}