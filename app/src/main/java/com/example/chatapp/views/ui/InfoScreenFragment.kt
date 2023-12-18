package com.example.chatapp.views.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentInfoScreenBinding
import com.example.chatapp.remoteRepository.models.ErrorModel
import com.example.chatapp.views.main.MainActivity


class InfoScreenFragment : BaseFragment() {
    lateinit var binding: FragmentInfoScreenBinding


    companion object {

        const val TAG = "InfoScreenFragment"
        const val INFO_TYPE = "INFO_TYPE"
        const val DATA = "DATA"

        fun newInstance(error: ErrorModel) =
            InfoScreenFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        (parentActivity as MainActivity).findViewById<CoordinatorLayout>(R.id.searcherBar).visibility = View.GONE
        (parentActivity as MainActivity).findViewById<LinearLayoutCompat>(R.id.browserContainer).visibility = View.GONE

        initEventListenr()
    }

    private fun initEventListenr() {
        binding.mainBtn.setOnClickListener {

        }
    }

}