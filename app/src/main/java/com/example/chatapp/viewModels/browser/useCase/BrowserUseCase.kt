package com.example.chatapp.viewModels.browser.useCase

import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.viewModels.login.IResponseProvider
import javax.inject.Inject

class BrowserUseCase @Inject constructor(): IBrowserUseCase {

    @Inject
    lateinit var provider: RemoteDataProvider


    override fun searchNewUser(token: String, value: String, res: IResponseProvider) {
        provider.searchNewUser(token, value, res)
    }


}