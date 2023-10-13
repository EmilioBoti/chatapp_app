package com.example.chatapp.views.ui.login.signin

import com.example.chatapp.remoteRepository.models.UserLogin

interface ILogin {
    fun signingIn(userLogin: UserLogin)
    fun singingUp(newUser: HashMap<String, String>)
    fun performSignIn(email: String, pw: String)
    fun performSignUp(name: String, email: String, pw: String)
}