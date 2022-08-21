package com.example.chatapp.viewModels.browser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.UserModel

class BrowserViewModels: ViewModel() {

    val listUserFound: MutableLiveData<MutableList<UserModel>> = MutableLiveData<MutableList<UserModel>>()


}