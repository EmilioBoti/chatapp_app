package com.example.chatapp.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentWelcomeBinding
import com.example.chatapp.views.ui.login.signin.LoginFragment
import com.example.chatapp.views.ui.login.signup.SignUpFragment


class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()


        binding.btnLogin.setOnClickListener {
            navigate(LoginFragment.newInstance())
        }

        binding.btnSignin.setOnClickListener {
            navigate(SignUpFragment.newInstance())
        }

    }

    private fun navigate(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.fragmentContainerView, fragment)
            ?.commit()
    }

}