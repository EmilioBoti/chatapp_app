package com.example.chatapp.views.ui.friend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityFriendBinding
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelAdapter
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.remoteRepository.IChatRepository
import com.example.chatapp.remoteRepository.RemoteDataProvider
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.viewModels.chat.useCase.IChatUseCase
import com.example.chatapp.viewModels.friend.FriendUseCase
import com.example.chatapp.viewModels.friend.FriendViewModel

class FriendActivity : AppCompatActivity() {
    lateinit var binding: ActivityFriendBinding

    lateinit var userCase: FriendUseCase
    lateinit var repository: IChatRepository
    lateinit var viewModel: FriendViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        repository = RemoteDataProvider(Utils().getRetrofitBuilder())
        userCase = FriendUseCase(repository)
        viewModel = FriendViewModel(userCase)


        viewModel.obtainUserFriends()

        observerData()
        eventHandle()
    }

    private fun eventHandle() {
        binding.toolbarFriends.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun observerData() {
        viewModel.listFriends.observe(this) {
            setAdapter(it)
        }
    }

    private fun setAdapter(contacts: MutableList<UserModel>) {

        val adapter = ModelAdapter<UserModel>(contacts, FactoryBuilder.FRIEND)
        adapter.setLayout(R.layout.friend_item)
        adapter.setListener(object : OnClickItem {
            override fun onClick(pos: Int) {
                viewModel.navigateChatRoom(this@FriendActivity, pos)
            }

            override fun onAccept(notification: NotificationModel, view: View, position: Int) {
            }

            override fun onReject(notification: NotificationModel, view: View, position: Int) {
            }

        })

        binding.friendContainer.apply {
            this.layoutManager = LinearLayoutManager(this@FriendActivity, RecyclerView.VERTICAL, false)
            this.adapter = adapter
        }
    }

}