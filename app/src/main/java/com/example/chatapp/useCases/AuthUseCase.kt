package com.example.chatapp.useCases

import com.example.chatapp.remoteRepository.Repository
import com.example.chatapp.remoteRepository.models.LoginResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.viewModels.login.IResponseProvider
import retrofit2.Call
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val provider: Repository): IAuthUseCase {


    override fun login(userLogin: UserLogin, res: IResponseProvider) {
        provider.login(userLogin, res)
    }

    override fun signIn(newUser: HashMap<String, String>): Call<LoginResponse> {
        return provider.signIn(newUser)
    }
}