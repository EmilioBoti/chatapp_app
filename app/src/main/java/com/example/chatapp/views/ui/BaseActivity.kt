package com.example.chatapp.views.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.R
import com.example.chatapp.api.SocketCon
import com.example.chatapp.databinding.ActivityBaseBinding
import com.example.chatapp.helpers.Session
import com.example.chatapp.views.home.HomeActivity
import com.example.chatapp.views.ui.login.LoginFragment

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        SocketCon.setSocket()
        checkLogin()

    }

    private fun loginFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, LoginFragment())
            .commit()
    }

    private fun checkLogin() {
        if (Session.getUserLogin(this) == true) {
            Intent(this, HomeActivity::class.java).apply {
                //this.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(this)
            }
            this.finish()
        } else {
            loginFragment()
        }
    }
}