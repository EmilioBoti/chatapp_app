package com.example.chatapp.views.ui.browser

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.App
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityBrowserBinding
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelAdapter
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.NewFriendEntity
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.viewModels.browser.BrowserViewModel
import com.example.chatapp.viewModels.browser.useCase.BrowserUseCase
import javax.inject.Inject

class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowserBinding
    private lateinit var browserViewModel: BrowserViewModel
    private lateinit var keyboard: InputMethodManager

    @Inject
    lateinit var useCase: BrowserUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        (application as App).getComponent().inject(this)
        setUpToolbar()
        keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        browserViewModel = BrowserViewModel(useCase, this.application)

        browserViewModel.listUserFound.observe(this, Observer {
            val adapter = ModelAdapter<NewFriendEntity>(it, FactoryBuilder.SEARCH)
            adapter.setLayout(R.layout.user_search_item)

            adapter.setListener(object : OnClickItem {
                override fun onClick(pos: Int) {
                    browserViewModel.sendRequest(pos)
                }

                override fun onAccept(notification: NotificationModel, view: View, position: Int) {
                }

                override fun onReject(notification: NotificationModel, view: View, position: Int) {
                }

            })

            binding.userContainer.apply {
                this.layoutManager = LinearLayoutManager(this@BrowserActivity)
                this.adapter = adapter
            }
        })

    }


    private fun setUpToolbar() {

        binding.toolbarBrowser.menu.findItem(R.id.searcher)
            .iconTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))

        binding.toolbarBrowser.setNavigationOnClickListener {
            this.onBackPressed()
        }

        binding.toolbarBrowser.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.searcher -> {
                    enableSearcher()
                }
            }
            true
        }

        binding.search.addTextChangedListener { edit ->
            edit?.let { text ->
                browserViewModel.search(text.toString())
            }
        }

    }

    private fun enableSearcher () {
        if (!binding.search.isVisible) {
            binding.search.visibility = View.VISIBLE
            binding.search.requestFocus()
            openKeyboard()
        }
    }

    private fun openKeyboard() {
        keyboard.showSoftInput(binding.search, InputMethodManager.RESULT_HIDDEN)
    }

    private fun closeKeyboard() {
        keyboard.hideSoftInputFromWindow(binding.search.windowToken, 0)
    }

    override fun onBackPressed() {
        when(binding.search.visibility) {
            View.VISIBLE -> {
                binding.search.visibility = View.GONE
                closeKeyboard()
            }
            View.GONE -> this.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        closeKeyboard()
    }
}