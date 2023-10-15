package com.example.chatapp.views.home.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.App
import com.example.chatapp.R
import com.example.chatapp.componentsUi.AppToolBarBuilder
import com.example.chatapp.componentsUi.AppToolBarListener
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelAdapter
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.viewModels.chat.useCase.IChatUseCaseProvider
import com.example.chatapp.views.home.chat.useCase.ChatUseCaseProvider
import com.example.chatapp.views.ui.BaseFragment
import com.example.chatapp.views.ui.chatRoom.ChatRoom


class ChatFragment : BaseFragment() {
    private lateinit var binding: FragmentChatBinding

    private var chatsAdapter: ModelAdapter<UserModel>? = null
    private val userCase: IChatUseCaseProvider = ChatUseCaseProvider()

    private val viewModel: ChatViewModel by viewModels {
        ChatViewModel.provideFactory(userCase)
    }


    companion object {
        const val TAG = "ChatFragment"
        const val USER_DATA = "data"

        fun newInstance() = ChatFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        (activity?.application as App).getComponent().inject(this)

        viewModel.getUserChats()

        updateUi()
    }

    private fun updateUi() {
        viewModel.chatList.observe(this.viewLifecycleOwner) {
            setAdapter(it)
        }
    }

    override fun initToolbar() {
        activity?.let {
            AppToolBarBuilder(it)
                .withActivity(it)
                .setSearchAction(true)
                .setSearchNotification(true)
                .setToolBarListener( object : AppToolBarListener {
                    override fun onSeachListener(input: String) {
                        Toast.makeText(activity, input, Toast.LENGTH_SHORT).show()
                    }
                })
                .build()
        }
    }
    private fun setAdapter(contacts: MutableList<UserModel>) {

        chatsAdapter = ModelAdapter<UserModel>(contacts, FactoryBuilder.CONTACT)
        chatsAdapter?.setLayout(R.layout.user_item_2)

        chatsAdapter?.setListener(object : OnClickItem {

            override fun onClick(pos: Int) {
                val user = viewModel.getUserSelected(pos)
                Intent(activity, ChatRoom::class.java).apply {
                    this.putExtra(USER_DATA, user)
                    activity?.startActivity(this)
                }
            }

            override fun onAccept(notification: NotificationModel, view: View, position: Int) {
            }

            override fun onReject(notification: NotificationModel, view: View, position: Int) {
            }

        })

        binding.userContainer.apply {
            this.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            this.adapter = chatsAdapter
        }
    }

}