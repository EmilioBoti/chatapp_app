package com.example.chatapp.useCases

import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse
import com.example.chatapp.remoteRepository.models.UserLogin
import retrofit2.Call
import retrofit2.Response

interface IAuthUseCase {
    suspend fun login(userLogin: UserLogin): Response<AuthApiResponse>
    fun signIn(newUser: HashMap<String, String>): Call<AuthApiResponse>
}