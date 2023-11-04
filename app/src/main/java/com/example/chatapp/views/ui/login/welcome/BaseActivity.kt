package com.example.chatapp.views.ui.login.welcome

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chatapp.App
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityBaseBinding
import com.example.chatapp.views.main.MainActivity
import javax.inject.Inject

class BaseActivity : AppCompatActivity(), IBaseViewPresenter {
    private lateinit var binding: ActivityBaseBinding

    @Inject
    lateinit var basePresenter: BasePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.fullScreen)
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        (application as App).getComponent().inject(this)
        basePresenter.setUp(this)
        window.statusBarColor = getColor(R.color.main_green)

    }

    override fun getBaseActivity(): Activity {
        return this
    }

    override fun navigateToLogin(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_in, R.anim.slide_out, R.anim.fade_out)
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    override fun <T> navigateToHome(screen: T) {
        val home = screen as MainActivity
        Intent(this, home::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}