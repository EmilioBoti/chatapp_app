package com.example.chatapp.views.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.chatapp.App
import com.example.chatapp.databinding.FragmentLogin3Binding
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.useCases.IAuthUseCase
import com.example.chatapp.viewModels.login.LoginViewModel
import javax.inject.Inject


class LoginFragment : BaseAuthFragment() {
    private lateinit var binding: FragmentLogin3Binding

    @Inject
    lateinit var modelProvider: IAuthUseCase

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModel.provideFactory(modelProvider)
    }

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentLogin3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as App).getComponent().inject(this)

        updateUi()
        eventsHandle()
    }

    private fun updateUi() {

        loginViewModel.navigation.observe(this.viewLifecycleOwner) {
            navigateToHome()
        }

        loginViewModel.errorAuth.observe(this.viewLifecycleOwner) { error ->
            showError(error)
        }
    }

    private fun eventsHandle() {

        binding.toolbarLogin.setNavigationOnClickListener {
            goBack()
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.validateInputs(
                UserLogin(binding.emailContainer.getEditInput().text.toString().trim(),
                binding.pwContainer.getEditInput().text.toString().trim())
            )
        }
    }

}