package com.example.chatapp.views.ui.login.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.chatapp.App
import com.example.chatapp.databinding.FragmentSignIn3Binding
import com.example.chatapp.useCases.IAuthUseCase
import com.example.chatapp.viewModels.login.IAuthPresenter
import com.example.chatapp.views.ui.login.signin.LoginViewModel
import com.example.chatapp.views.home.HomeActivity
import com.example.chatapp.views.ui.login.BaseAuthFragment
import javax.inject.Inject

class SignUpFragment : BaseAuthFragment() {
    private lateinit var binding: FragmentSignIn3Binding

    @Inject
    lateinit var modelProvider: IAuthUseCase

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModel.provideFactory(modelProvider)
    }

    companion object {
        const val TAG = ""

        fun newInstance() : SignUpFragment = SignUpFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSignIn3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App).getComponent().inject(this)

        loginViewModel.navigation.observe(this.viewLifecycleOwner) {
            navigateToHome()
        }

        loginViewModel.errorAuth.observe(this.viewLifecycleOwner) { error ->
            showError(error)
        }

        binding.btnSignUp.setOnClickListener {
            val name = binding.nameInput.getEditInput().text.toString().trim()
            val email = binding.emailInput.getEditInput().text.toString().trim()
            val pw = binding.pwInput.getEditInput().text.toString().trim()

            val newUser = HashMap<String, String>()
            newUser["name"] = name
            newUser["email"] = email
            newUser["pw"] = pw

            loginViewModel.registerUser(newUser)
        }
    }

    override fun onStart() {
        super.onStart()

        eventHandle()
    }

    private fun eventHandle() {

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