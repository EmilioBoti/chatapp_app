package com.example.chatapp.views.ui.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.chatapp.App
import com.example.chatapp.databinding.FragmentSignIn3Binding
import com.example.chatapp.useCases.IAuthUseCase
import com.example.chatapp.views.ui.login.signin.LoginViewModel
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
        const val TAG = "SignUpFragment"

        fun newInstance() : SignUpFragment = SignUpFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
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

            loginViewModel.performSignUp(name, email, pw)
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

}