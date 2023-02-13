package com.example.chatapp.viewModels.login

import com.example.chatapp.componentsUi.TypeInput

interface InputMethod {
    fun setUp()
    fun setIcon(icon: Int, iconColor: Int?)
    fun setInputType(type: TypeInput)
    fun setBgColor(bgColor: Int)
    fun setIconTint(tintColor: Int)
    fun setHintColor(hintColor: Int)
    fun setStrokeColor(strokeColor: Int)
    fun setCursorColor(cursorColor: Int)
}