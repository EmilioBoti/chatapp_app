package com.example.chatapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.helpers.OnClickItem
import com.example.chatapp.helpers.Session
import com.example.chatapp.model.UserModel
import com.example.chatapp.viewModels.home.HomeViewModel
import com.example.chatapp.viewModels.home.UserAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Chats"

    }

    override fun onStart() {
        super.onStart()

        homeViewModel.contacts.observe(this, Observer {
            setAdapter(it)
        })

        val id: String? = Session.getUserId(this)
        id?.let { homeViewModel.updateSocket(it) }

        val currentUser: String? = Session.getUserId(this)
        currentUser?.let {
            homeViewModel.getContacts(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {
                homeViewModel.logout(this)
            }
        }
        return true
    }

    private fun setAdapter(contacts: MutableList<UserModel>) {
         val adapter = UserAdapter(contacts, object : OnClickItem {
             override fun onClick(pos: Int) {
                 homeViewModel.navigateChatRoom(this@HomeActivity, pos)
             }
         })

        binding.userContainer.apply {
            this.layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
            this.addItemDecoration(DividerItemDecoration(this@HomeActivity, RecyclerView.VERTICAL))
            this.adapter = adapter
        }
    }
}
