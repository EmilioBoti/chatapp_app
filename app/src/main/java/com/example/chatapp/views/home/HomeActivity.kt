package com.example.chatapp.views.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
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
import com.example.chatapp.views.ui.notification.NotificationActivity
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeUseCase: HomeUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Chats"

    }

    override fun onStart() {
        super.onStart()

        (this.application as App).getComponent().inject( this)

        homeViewModel = HomeViewModel(homeUseCase, this.application)

        homeViewModel.contacts.observe(this, Observer {
            setAdapter(it)
        })

        binding.btnBrowser.setOnClickListener {
            Intent(this, BrowserActivity::class.java).apply {
                startActivity(this)
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> alertToLogout()
            R.id.notifications -> {
                Intent(this, NotificationActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return true
    }

    private fun setAdapter(contacts: MutableList<UserModel>) {

        val adapter = ModelAdapter<UserModel>(contacts, FactoryBuilder.CONTACT)
        adapter.setLayout(R.layout.user_item_2)
        adapter.setListener(object : OnClickItem {
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
            this.adapter = adapter
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

    override fun onBackPressed() {
        super.onBackPressed()
        homeViewModel.disconnectSocket()
    }
}
