package com.example.chatapp.viewModels.login

import com.example.chatapp.remoteRepository.models.UserLogin

interface ILogin {
    fun login(userLogin: UserLogin)
    fun singin(newUser: HashMap<String, String>)
}