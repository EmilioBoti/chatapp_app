package com.example.chatapp.useCases

import com.example.chatapp.remoteRepository.models.LoginResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.viewModels.login.IResponseProvider
import retrofit2.Call

interface IAuthUseCase {
    fun login(userLogin: UserLogin, res: IResponseProvider)
    fun signIn(newUser: HashMap<String, String>): Call<LoginResponse>
}