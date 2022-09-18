package com.example.chatapp.views.ui.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.chatapp.databinding.FragmentSignInBinding
import com.example.chatapp.helpers.Session
import com.example.chatapp.viewModels.login.LoginViewModel
import com.example.chatapp.views.home.HomeActivity

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val regexEmail: String = "^[A-Za-z0-9]+@([a-zA-Z]+)(.)[a-zA-Z]{1,3}$"
    private val regexEmail2: String = "^[A-Za-z]([@]{1})(.{1})(\\.)(/{1,})"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginViewModel.user.observe(this.viewLifecycleOwner, Observer { newUser ->
            activity?.let {
                Session.saveUser(it, newUser)
                Intent(it, HomeActivity::class.java).apply {
                    startActivity(this)
                }
                it.finish()
            }
        })


        binding.btnSignIn.setOnClickListener {
            val name = binding.nameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val pw = binding.pwInput.text.toString().trim()

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



}