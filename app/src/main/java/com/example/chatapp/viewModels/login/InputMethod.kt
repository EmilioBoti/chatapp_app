package com.example.chatapp.viewModels.login

import com.example.chatapp.componentsUi.TypeInput

interface InputMethod {
    fun setUp()
    fun setIcon(icon: Int, iconColor: Int?)
    fun setInputType(type: TypeInput)
}