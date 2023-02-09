package com.example.chatapp.viewModels.browser.useCase

import com.example.chatapp.viewModels.login.IResponseProvider

interface IBrowserUseCase {
    fun searchNewUser(token: String, value: String, res: IResponseProvider)
}