package com.example.chatapp.views.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
import com.example.chatapp.helpers.Session
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.viewModels.chat.useCase.IChatUseCaseProvider
import com.example.chatapp.views.main.chat.useCase.ChatUseCaseProvider
import com.example.chatapp.views.ui.BaseFragment
import com.example.chatapp.views.ui.chatRoom.ChatRoom
import com.example.chatapp.views.ui.login.welcome.BaseActivity
import com.example.chatapp.views.ui.notification.NotificationActivity


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
        parentActivity?.let {
            AppToolBarBuilder(it)
                .withActivity(it)
                .setSearchAction(true)
                .setSearchNotification(true)
                .setToolBarListener( object : AppToolBarListener {
                    override fun onSeachListener(input: String) {
                        viewModel.searchChat(input)
                    }

                    override fun onClickNotification() {
                        navigateTo(NotificationActivity::class, null)
                    }

                    override fun onClickLogout() {
                        AlertDialog.Builder(it)
                            .setTitle(R.string.logout)
                            .setMessage(R.string.logoutMessage)
                            .setIcon(R.drawable.logout_24)
                            .setNegativeButton("Cancel") { p0, p1 ->

                            }
                            .setPositiveButton("Accept") { p0, p1 ->
                                Session.logout(it)
                                navigateTo(BaseActivity::class, null, true)
                            }
                            .show()
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
                navigateTo(ChatRoom::class, user)
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