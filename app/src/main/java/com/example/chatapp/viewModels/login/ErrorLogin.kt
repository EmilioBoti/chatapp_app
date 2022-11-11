package com.example.chatapp.viewModels.login

import com.example.chatapp.R

/**
 *  @author Emilio
 *  @constructor take a parameter type of Error's enum class
 */
class ErrorLogin(private val error: Error) {

    /**
     * @return reference's resource of error value
     */
    fun getError(): Int {
        return when(error) {
            Error.EMAIL_ERROR -> R.string.email_error
            Error.PASSWORD_ERROR -> R.string.password_error
            Error.NET_ERROR -> R.string.net_error
            Error.USER_NOT_EXIST_ERROR -> R.string.user_not_exist_error
        }
    }
}