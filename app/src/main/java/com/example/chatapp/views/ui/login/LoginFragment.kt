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
import com.example.chatapp.databinding.FragmentLoginBinding
import com.example.chatapp.helpers.Session
import com.example.chatapp.repositoryApi.login.UserLogin
import com.example.chatapp.viewModels.login.LoginViewModel
import com.example.chatapp.views.home.HomeActivity
import com.example.chatapp.views.ui.signin.SignInFragment

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginViewModel.user.observe(this.viewLifecycleOwner , Observer { userModel ->
            activity?.let {
                Session.saveUser(it, userModel)
                Intent(it, HomeActivity::class.java).apply {
                    startActivity(this)
                }
                it.finish()
            }
        })

        loginViewModel.error.observe(this.viewLifecycleOwner, Observer {
            Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
        })


        binding.btnLogin.setOnClickListener {
            loginViewModel.login(UserLogin(binding.emailInput.text.toString().trim(),
                binding.pwInput.text.toString().trim()))
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