package com.example.chatapp.views.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chatapp.App
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityBaseBinding
import com.example.chatapp.viewModels.home.BasePresenter
import com.example.chatapp.viewModels.home.IBaseViewPresenter
import com.example.chatapp.views.ui.login.LoginFragment
import javax.inject.Inject

class BaseActivity : AppCompatActivity(), IBaseViewPresenter {
    private lateinit var binding: ActivityBaseBinding

    @Inject
    lateinit var basePresenter: BasePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        (application as App).getComponent().inject(this)
        basePresenter.setUp(this)

    }

    override fun getBaseActivity(): Activity {
        return this
    }

    override fun navigateToLogin(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, LoginFragment())
            .commit()
    }

    override fun <T> navigateToHome(screen: T) {
        val home = screen as HomeActivity
        Intent(this, home::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}