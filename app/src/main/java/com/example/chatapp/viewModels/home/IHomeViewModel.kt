package com.example.chatapp.viewModels.home

interface IHomeViewModel {
    fun updateSocket(id: String)
    fun getContacts(currentUser: String)
}