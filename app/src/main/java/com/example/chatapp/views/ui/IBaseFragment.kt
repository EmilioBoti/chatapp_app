package com.example.chatapp.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

interface IBaseFragment {

    fun initToolbar()
    fun navigateTo(layoutId: Int, fragment: Fragment, tag: String)
    fun navigateTo(screen: KClass<* >, data: Bundle?, finish: Boolean = false)
    fun showErrorInfoScreen(layoutId: Int ,fragment: Fragment, tag: String)

}