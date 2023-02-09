package com.example.chatapp.viewModels.home

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