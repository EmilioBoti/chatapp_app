package com.example.chatapp.viewModels.login

import android.app.Application
import com.example.chatapp.remoteRepository.models.UserLogin
import com.example.chatapp.useCases.IAuthUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    @RelaxedMockK
    private lateinit var provider: IAuthUseCase

    @RelaxedMockK
    private lateinit var context: Application

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var authPresenter: AuthPresenter

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        authPresenter = AuthPresenter(context)
        loginViewModel = LoginViewModel(provider, authPresenter)
    }


    @Test
    fun `check is viewModel login method is call`() {
        val user = UserLogin("emilio@gmail.com", "12345")

        loginViewModel.validateInputs(user)

        every { loginViewModel.login(any()) }

    }

    @Test
    fun `check is provider singIn method is call`() {
        val user = HashMap<String, String>()

        loginViewModel.singin(user)

        verify { provider.signIn(any()) }

    }

    @Test
    fun `check is provider login method is call`() {
        val user = UserLogin("emilio@gmail.com", "12345")

        loginViewModel.login(user)

        verify { provider.login(any(), any()) }

    }

    @Test
    fun `check is email is correct`() {
        assertTrue(loginViewModel.validateEmail("emilio@gmail.com"))
    }

    @Test
    fun `check is email is not correct`() {
        assertFalse(loginViewModel.validateEmail("emilidogmail.com"))
    }

}