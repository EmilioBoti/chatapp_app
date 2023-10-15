package com.example.chatapp.views.home.chat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.chat.useCase.IChatUseCaseProvider
import com.example.chatapp.viewModels.network.State
import kotlinx.coroutines.launch

class ChatViewModel(private val userCase: IChatUseCaseProvider): SocketEvent(), IChatViewModel {

    private var _chatList: MutableLiveData<MutableList<UserModel>> = MutableLiveData<MutableList<UserModel>>()
    var chatList: LiveData<MutableList<UserModel>> = _chatList


    companion object {
        fun provideFactory(userCase: IChatUseCaseProvider): ViewModelProvider.Factory  {
            return object : ViewModelProvider.Factory {

                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    return ChatViewModel(userCase) as T
                }
            }
        }
    }

    override fun getUserChats() {
        viewModelScope.launch {
            try {
                val result = userCase.getUserChats(token)

                if (result.isSuccessful) {
                    _chatList.postValue(result.body())
                }

            } catch (e: Exception) {
                throw e
            }
        }
    }

    override fun getUserSelected(pos: Int): Bundle {
        val user: UserModel? = _chatList.value?.get(pos)
        return Bundle().apply {
            putString(Const.ROOM_ID, user?.roomId)
            putString(Const.ID_USER, user?.id)
            putString(Const.NAME_USER, user?.name)
        }
    }
    override fun receiveMessage(message: MessageModel) {
    }

    override fun receiveNotifications(notification: HashMap<String, String>) {
    }

    override fun isConnectivityAvailable(state: State) {
    }

}