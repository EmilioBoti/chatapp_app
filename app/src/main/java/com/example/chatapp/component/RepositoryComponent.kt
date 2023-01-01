package com.example.chatapp.component

import com.example.chatapp.module.RepositoryModule
import com.example.chatapp.viewModels.browser.BrowserViewModel
import com.example.chatapp.viewModels.businessLogic.notification.SocketEvent
import com.example.chatapp.viewModels.chat.ChatViewModel
import com.example.chatapp.viewModels.home.HomeViewModel
import com.example.chatapp.viewModels.login.LoginViewModel
import com.example.chatapp.viewModels.notifications.NotificationViewModel
import com.example.chatapp.views.home.BaseActivity
import com.example.chatapp.views.ui.login.LoginFragment
import com.example.chatapp.views.ui.signin.SignInFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {
    fun inject(homeViewModel: HomeViewModel)
    fun inject(browserViewModel: BrowserViewModel)
    fun inject(browserViewModel: ChatViewModel)
    fun inject(browserViewModel: LoginViewModel)
    fun inject(browserViewModel: NotificationViewModel)
    fun inject(socketEvent: SocketEvent)
    fun inject(baseActivity: BaseActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(signFragment: SignInFragment)
}