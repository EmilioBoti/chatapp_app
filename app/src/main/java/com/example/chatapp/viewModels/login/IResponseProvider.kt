package com.example.chatapp.viewModels.login

interface IResponseProvider {
    fun <T> response(data: T)
    fun responseError(err: ErrorLogin)
}