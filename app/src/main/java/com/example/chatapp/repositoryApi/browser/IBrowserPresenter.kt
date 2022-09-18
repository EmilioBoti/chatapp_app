package com.example.chatapp.repositoryApi.browser

interface IBrowserPresenter {
    fun search(value: String)
    fun sendRequest(pos: Int)
}