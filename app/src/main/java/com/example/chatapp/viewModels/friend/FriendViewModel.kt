package com.example.chatapp.viewModels.friend

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.viewModels.router.Router
import com.example.chatapp.views.ui.chatRoom.ChatRoom
import kotlinx.coroutines.launch

class FriendViewModel(private val userCase: FriendUseCase): ViewModel() {
    var listFriends: MutableLiveData<MutableList<UserModel>> = MutableLiveData()
    private lateinit var token: String
    private val router: Router = Router()

    companion object {
        const val DATA_USER: String = "data"
    }

    fun obtainUserFriends() {
        
        viewModelScope.launch {
            Session.getToken()?.let { token = it }
            val result = userCase.retrieveUserFriends(token)
            listFriends.postValue(result?.body)
        }

    }

    fun navigateChatRoom(activity: Activity, pos: Int) {
        val userModel: UserModel? = listFriends.value?.get(pos)
        val data: Bundle = Bundle().apply {
            putString(Const.ROOM_ID, userModel?.roomId)
            putString(Const.ID_USER, userModel?.id)
            putString(Const.NAME_USER, userModel?.name)
            putString(Session.SOCKETID, userModel?.socketId)
        }
        router.navigateTo(ChatRoom(), activity, data)
    }
}