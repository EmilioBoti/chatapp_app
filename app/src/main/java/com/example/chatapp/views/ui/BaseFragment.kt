package com.example.chatapp.views.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.chatapp.R
import com.example.chatapp.componentsUi.AppToolBarBuilder
import com.example.chatapp.componentsUi.AppToolBarListener


open class BaseFragment : Fragment(), IBaseFragment {


    companion object {
        const val TAG = "BaseFragment"

        fun newInstance() = BaseFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onStart() {
        super.onStart()

        initToolbar()
    }

    override fun initToolbar() {
        activity?.let {
            AppToolBarBuilder(it)
                .withActivity(it)
                .build()
        }
    }

}