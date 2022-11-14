package com.example.chatapp.views.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.chatapp.databinding.FragmentSignIn2Binding
import com.example.chatapp.helpers.Session
import com.example.chatapp.viewModels.login.LoginViewModel
import com.example.chatapp.views.home.HomeActivity

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignIn2Binding
    private val loginViewModel: LoginViewModel by viewModels()
    private val regexEmail: String = "^[A-Za-z0-9]+@([a-zA-Z]+)(.)[a-zA-Z]{1,3}$"
    private val regexEmail2: String = "^[A-Za-z]([@]{1})(.{1})(\\.)(/{1,})"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSignIn2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginViewModel.user.observe(this.viewLifecycleOwner, Observer { newUser ->
            activity?.let {
                Session.saveUser(it, newUser)
                navigateTo(HomeActivity())
            }
        })



        binding.btnSignIn.setOnClickListener {
            val name = binding.nameInput.getEditInput().text.toString().trim()
            val email = binding.emailInput.getEditInput().text.toString().trim()
            val pw = binding.pwInput.getEditInput().text.toString().trim()

            val newUser = HashMap<String, String>()
            newUser["name"] = name
            newUser["email"] = email
            newUser["pw"] = pw

            loginViewModel.registerUser(newUser)
        }

        binding.toolbarSignIn.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun navigateTo(screen: Activity) {
        activity?.let {
            Intent(it, screen::class.java).apply {
                startActivity(this)
            }
            it.finish()
        }
    }

}