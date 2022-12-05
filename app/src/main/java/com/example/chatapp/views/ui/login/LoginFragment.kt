package com.example.chatapp.views.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentLogin2Binding
import com.example.chatapp.helpers.Session
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.viewModels.login.LoginViewModel
import com.example.chatapp.views.home.HomeActivity
import com.example.chatapp.views.ui.signin.SignInFragment

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLogin2Binding
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentLogin2Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginViewModel.user.observe(this.viewLifecycleOwner , Observer { token ->
            activity?.let {
                Intent(it, HomeActivity::class.java).apply {
                    startActivity(this)
                }
                it.finish()
            }
        })

        loginViewModel.error.observe(this.viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity, getString(error.getError()), Toast.LENGTH_LONG).show()
        })

        binding.btnLogin.setOnClickListener {
            loginViewModel.validateInputs(
                UserLogin(binding.emailContainer.getEditInput().text.toString().trim(),
                binding.pwContainer.getEditInput().text.toString().trim())
            )
        }

        binding.btnSignIn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack("signIn")
                ?.setCustomAnimations(R.anim.slide_in,R.anim.fade_out, R.anim.slide_out, R.anim.fade_in)
                ?.replace(R.id.fragmentContainerView, SignInFragment())
                ?.commit()
        }

    }

}