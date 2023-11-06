package com.example.chatapp.component

import com.example.chatapp.module.RepositoryModule
import com.example.chatapp.viewModels.browser.BrowserViewModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.messengerChat.MessengerChatViewModel
import com.example.chatapp.views.main.MainViewModel
import com.example.chatapp.views.ui.login.signin.LoginViewModel
import com.example.chatapp.viewModels.notifications.NotificationViewModel
import com.example.chatapp.views.ui.login.welcome.BaseActivity
import com.example.chatapp.views.main.MainActivity
import com.example.chatapp.views.main.chat.ChatFragment
import com.example.chatapp.views.ui.browser.BrowserActivity
import com.example.chatapp.views.ui.chatRoom.ChatRoom
import com.example.chatapp.views.ui.login.signin.LoginFragment
import com.example.chatapp.views.ui.notification.NotificationActivity
import com.example.chatapp.views.ui.login.signup.SignUpFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {
    fun inject(homeViewModel: MainViewModel)
    fun inject(browserViewModel: BrowserViewModel)
    fun inject(browserViewModel: MessengerChatViewModel)
    fun inject(browserViewModel: LoginViewModel)
    fun inject(browserViewModel: NotificationViewModel)
    fun inject(socketEvent: SocketEvent)
    fun inject(baseActivity: BaseActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(signFragment: SignUpFragment)
    fun inject(notificationActivity: NotificationActivity)
    fun inject(homeActivity: MainActivity)
    fun inject(chatRoom: ChatRoom)
    fun inject(browserActivity: BrowserActivity)
    fun inject(chatFragment: ChatFragment)
}