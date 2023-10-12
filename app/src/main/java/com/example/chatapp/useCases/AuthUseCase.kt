package com.example.chatapp.useCases

import com.example.chatapp.remoteRepository.Repository
import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val provider: Repository): IAuthUseCase {


    override suspend fun login(userLogin: UserLogin): Response<AuthApiResponse> {
        return provider.login(userLogin)
    }

    override fun signIn(newUser: HashMap<String, String>): Call<AuthApiResponse> {
        return provider.signIn(newUser)
    }
}