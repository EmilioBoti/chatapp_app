package com.example.chatapp.views.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chatapp.remoteRepository.models.ApiError
import com.example.chatapp.views.home.HomeActivity



open class BaseAuthFragment: Fragment(), IBaseAuthView {


    override fun navigateToHome() {
        activity?.let {
            Intent(it, HomeActivity::class.java).apply {
                startActivity(this)
            }
            it.finish()
        }
    }

    override fun goBack() {
        activity?.onBackPressed()
    }

    override fun showError(error: ApiError) {
        activity?.let {
            Toast.makeText(activity, error.cause, Toast.LENGTH_LONG).show()
        }
    }

    override fun showError() {
        Toast.makeText(activity, "Something is wrong", Toast.LENGTH_LONG).show()
    }

}