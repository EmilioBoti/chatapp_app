package com.example.chatapp.viewModels.login

import com.example.chatapp.repositoryApi.login.UserLogin

interface ILogin {
    fun login(userLogin: UserLogin)
    fun singin(newUser: HashMap<String, String>)
}