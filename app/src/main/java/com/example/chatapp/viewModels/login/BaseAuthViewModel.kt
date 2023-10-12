package com.example.chatapp.viewModels.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.helpers.enums.Navigation
import com.example.chatapp.remoteRepository.models.ApiError
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.net.ConnectException


open class BaseAuthViewModel : ViewModel(), IBaseAuthViewModel {


    private val _navigation: MutableLiveData<Navigation> = MutableLiveData<Navigation>()
    val navigation: MutableLiveData<Navigation> = _navigation
    val errorAuth: MutableLiveData<ApiError> = MutableLiveData<ApiError>()


    override fun navigateTo(navigation: Navigation) {
        this.navigation.postValue(navigation)
    }

    override fun showAuthError(error: ApiError) {
        this.errorAuth.postValue(error)
    }

    override fun parseApiError(error: ResponseBody?): ApiError {
        return try {
            val erro = Gson().fromJson(error?.string(), ApiError::class.java)
            erro
        } catch (e: Exception) {
            ApiError("unknown", "", -1)
        }
    }

    override fun checkError(error: Exception) {
        if (error is ConnectException) {
            this.errorAuth.postValue(ApiError(
                "netwotk",
                "Something went wrong",
                500))
        }
    }


}