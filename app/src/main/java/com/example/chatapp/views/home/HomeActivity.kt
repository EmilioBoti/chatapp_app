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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.App
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMain2Binding
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelAdapter
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.viewModels.home.HomeViewModel
import com.example.chatapp.viewModels.home.useCase.HomeUseCase
import com.example.chatapp.views.ui.browser.BrowserActivity
import com.example.chatapp.views.ui.friend.FriendActivity
import com.example.chatapp.views.ui.notification.NotificationActivity
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    private lateinit var keyboard: InputMethodManager
    private var chatsAdapter: ModelAdapter<UserModel>? = null

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

        keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        homeViewModel.getContacts()
        homeViewModel.contacts.observe(this, Observer {
            setAdapter(it)
        })

        eventHandler()
    }

    override fun onPause() {
        super.onPause()

        hideSearcherInput()
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

        binding.searcherInput.setOnFocusChangeListener { view, b ->
            if (!b) {
                binding.searcherInput.visibility = View.GONE
            }
        }

        binding.searcherInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                homeViewModel.findYourFriends(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        binding.toolbar.setOnMenuItemClickListener { menu ->
            when(menu.itemId) {
                R.id.logout -> alertToLogout()
                R.id.notifications -> navigate(NotificationActivity())
                R.id.search -> {
                    if (!binding.searcherInput.isVisible) {
                        showSearcherInput()
                    }
                }
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            this.onBackPressed()
        }

    }

    private fun <T> navigate(screen: T) {
        screen?.let {
            Intent(this, it::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setAdapter(contacts: MutableList<UserModel>) {

        chatsAdapter = ModelAdapter<UserModel>(contacts, FactoryBuilder.CONTACT)
        chatsAdapter?.setLayout(R.layout.user_item_2)
        chatsAdapter?.setListener(object : OnClickItem {
            override fun onClick(pos: Int) {
                homeViewModel.navigateChatRoom(this@HomeActivity, pos)
            }

            override fun onAccept(notification: NotificationModel, view: View, position: Int) {
            }

            override fun onReject(notification: NotificationModel, view: View, position: Int) {
            }

        })

        binding.userContainer.apply {
            this.layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
            this.adapter = chatsAdapter
        }
    }

    private fun showSearcherInput() {
        binding.searcherInput.run {
            visibility = View.VISIBLE
            requestFocus()
        }
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back_24)
        keyboard.showSoftInput(binding.searcherInput, InputMethodManager.RESULT_SHOWN)
    }

    private fun hideSearcherInput() {
        binding.searcherInput.setText("")
        homeViewModel.findYourFriends("")
        binding.searcherInput.visibility = View.GONE
        keyboard.hideSoftInputFromWindow(binding.searcherInput.windowToken, 0)
        binding.toolbar.navigationIcon = null
        binding.btnFriends.hide()
        binding.btnBrowser.hide()
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

    override fun onBackPressed() {
        if (binding.searcherInput.isVisible) {
            hideSearcherInput()
        } else {
            homeViewModel.disconnectSocket()
            super.onBackPressed()
        }
    }
}
