package com.example.chatapp.viewModels.businessLogic.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.api.SocketCon
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.enums.TypeError
import com.example.chatapp.remoteRepository.models.ApiError
import com.example.chatapp.remoteRepository.models.ErrorModel
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.remoteRepository.models.convertToErrorModel
import com.example.chatapp.viewModels.network.State
import com.google.gson.Gson
import io.socket.client.Socket
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class SocketEvent : ViewModel() {
    protected var mSocket: Socket = SocketCon.getSocket()
    protected var token: String = Session.getToken()
    protected var appError: MutableLiveData<ErrorModel> = MutableLiveData()


    init {
        eventListener()
        getConnectivity()
    }

    fun checkApiError(error: Exception? = null, apiError: Response<MutableList<UserModel>>?) {
        if (error is UnknownHostException || error is ConnectException ||
            error is SocketTimeoutException ) {

            val errorMesage = when(error) {
                is ConnectException -> {
                    "connection error"
                }
                else -> { "Something went wrong." }
            }
            appError.postValue(
                ErrorModel(
                    TypeError.POP_UP,
                    errorMesage,
                    "",
                    -1
                )
            )
        } else {
            val errorApi = parseApiErro(apiError)
            val er = when(errorApi.status) {
                401 -> {
                    errorApi.convertToErrorModel(TypeError.POP_UP)
                } else -> {
                    errorApi.convertToErrorModel(TypeError.UNKNOWN)
                }
            }
            appError.postValue(er)
        }

    }

    private fun parseApiErro(apiError: Response<MutableList<UserModel>>?): ApiError {
        var errorApi: ApiError = ApiError("unknown", "", -1)
        return try {
            errorApi = Gson().fromJson(apiError?.errorBody().toString(), ApiError::class.java)
            errorApi
        } catch (err: Exception) {
            errorApi
        }
    }

    private fun getConnectivity() {}

    private fun eventListener() {}

    abstract fun receiveMessage(message: MessageModel)
    abstract fun receiveNotifications(notification: HashMap<String, String>)
    abstract fun isConnectivityAvailable(state: State)
}