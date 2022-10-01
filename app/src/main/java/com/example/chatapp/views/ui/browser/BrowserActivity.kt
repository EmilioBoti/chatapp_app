package com.example.chatapp.views.ui.browser

import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityBrowserBinding
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelAdapter
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.repositoryApi.models.NotificationModel
import com.example.chatapp.repositoryApi.models.UserModel
import com.example.chatapp.viewModels.browser.BrowserViewModel

class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowserBinding
    private val browserViewModel: BrowserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        setUpToolbar()


        browserViewModel.listUserFound.observe(this, Observer {
            val adapter = ModelAdapter<UserModel>(it, FactoryBuilder.SEARCH)
            adapter.setLayout(R.layout.user_search_item)

            adapter.setListener(object : OnClickItem {
                override fun onClick(pos: Int) {
                    browserViewModel.sendRequest(pos)
                }

                override fun onAccept(notification: NotificationModel) {
                    TODO("Not yet implemented")
                }

                override fun onReject(notification: NotificationModel) {
                    TODO("Not yet implemented")
                }

            })

            binding.userContainer.apply {
                this.layoutManager = LinearLayoutManager(this@BrowserActivity)
                this.adapter = adapter
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpToolbar() {
        binding.toolbarBrowser.menu.findItem(R.id.searcher)
            .iconTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))

        binding.toolbarBrowser.setNavigationOnClickListener {
            this.onBackPressed()
        }

        binding.toolbarBrowser.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.searcher -> {
                    binding.search.visibility = View.VISIBLE
                    binding.search.requestFocus()
                }
            }
            true
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    browserViewModel.search(it)
                }
                return true
            }

        })

    }

    override fun onBackPressed() {

        when(binding.search.visibility) {
            View.VISIBLE -> binding.search.visibility = View.GONE
            View.GONE -> this.finish()
        }
    }
}