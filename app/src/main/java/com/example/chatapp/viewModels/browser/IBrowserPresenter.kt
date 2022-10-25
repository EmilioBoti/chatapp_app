package com.example.chatapp.viewModels.browser

interface IBrowserPresenter {
    fun search(value: String)
    fun sendRequest(pos: Int)
}