package com.example.chatapp.views.ui.browser

import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityBrowserBinding

class BrowserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        setUpToolbar()

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
    }

    override fun onBackPressed() {

        if (binding.search.visibility == View.VISIBLE) {
            binding.search.visibility = View.GONE
        } else {
            this.finish()
        }
    }
}