package com.example.chatapp.views.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.chatapp.App
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMain2Binding
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelAdapter
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.views.home.chat.ChatFragment
import com.example.chatapp.views.home.useCase.HomeUseCase
import com.example.chatapp.views.ui.browser.BrowserActivity
import com.example.chatapp.views.ui.friend.FriendActivity
import com.example.chatapp.views.ui.notification.NotificationActivity
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    @Inject
    lateinit var homeUseCase: HomeUseCase

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.provideFactory(homeUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        (this.application as App).getComponent().inject( this)

        setCurrentScreen()

        eventHandler()
    }

    private fun setCurrentScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.hostMainContainer, ChatFragment.newInstance())
            .commit()
    }

    private fun eventHandler() {

        binding.btnBrowser.setOnClickListener {
            navigate<BrowserActivity>(BrowserActivity())
        }

        binding.btnFriends.setOnClickListener {
            navigate<FriendActivity>(FriendActivity())
        }

        binding.btnOptions.setOnClickListener {
            if (binding.btnBrowser.isVisible) {
                binding.btnFriends.hide()
                binding.btnBrowser.hide()
            } else {
                binding.btnFriends.show()
                binding.btnBrowser.show()
            }
        }
    }

    private fun <T> navigate(screen: T) {
        screen?.let {
            Intent(this, it::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun alertToLogout() {
        val alert = AlertDialog.Builder(this)
            .setTitle(R.string.logout)
            .setMessage(R.string.logoutMessage)
            .setIcon(R.drawable.logout_24)
            .setNegativeButton("Cancel") { p0, p1 ->

            }
            .setPositiveButton("Accept") { p0, p1 ->
                homeViewModel.logout(this)
            }
            alert.show()
    }

}
