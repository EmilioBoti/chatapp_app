package com.example.chatapp.views.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.chatapp.R
import com.example.chatapp.remoteRepository.models.ApiError
import com.example.chatapp.views.main.MainActivity


class InfoScreenFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_screen, container, false)
    }

    override fun onStart() {
        super.onStart()

        (parentActivity as MainActivity).findViewById<CoordinatorLayout>(R.id.searcherBar).visibility = View.GONE
        (parentActivity as MainActivity).findViewById<LinearLayoutCompat>(R.id.browserContainer).visibility = View.GONE
    }

    companion object {

        const val TAG = "InfoScreenFragment"

        fun newInstance(error: ApiError) =
            InfoScreenFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}