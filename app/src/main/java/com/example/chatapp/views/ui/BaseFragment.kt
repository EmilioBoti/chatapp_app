package com.example.chatapp.views.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.componentsUi.AppToolBarBuilder
import com.example.chatapp.helpers.Session
import com.example.chatapp.remoteRepository.models.ErrorModel
import com.example.chatapp.viewModels.friend.FriendViewModel
import com.example.chatapp.views.main.MainActivity
import com.example.chatapp.views.ui.login.welcome.BaseActivity
import kotlin.reflect.KClass


open class BaseFragment : Fragment(), IBaseFragment {

    protected var parentActivity: MainActivity? = null


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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MainActivity) {
            parentActivity = context as MainActivity
        }
    }

    override fun onStart() {
        super.onStart()

        initToolbar()
    }

    override fun initToolbar() {
        parentActivity?.let {
            AppToolBarBuilder(it)
                .withActivity(it)
                .build()
        }
    }

    override fun navigateTo(screen: KClass<*>, data: Bundle?, finish: Boolean) {
        parentActivity?.let {
            Intent(it, screen.java).apply {
                this.putExtra(FriendViewModel.DATA_USER, data)
                it.startActivity(this)
            }
            if (finish) it.finish()
        }
    }

    override fun showErrorInfoScreen(layoutId: Int ,fragment: Fragment, tag: String) {
        parentActivity?.supportFragmentManager?.beginTransaction()
            ?.replace(layoutId, fragment)
            ?.commit()
    }

    override fun navigateTo(layoutId: Int ,fragment: Fragment, tag: String) {
        parentActivity?.supportFragmentManager?.beginTransaction()
            ?.replace(layoutId, fragment)
            ?.addToBackStack(tag)
            ?.commit()
    }

    fun logoutUser() {
        parentActivity?.let { activity ->
            Session.logout(activity.applicationContext)
            Intent(activity, BaseActivity::class.java).apply {
                activity.startActivity(this)
            }
            activity.finish()
        }
    }

    fun infoScreenAlert(errorModel: ErrorModel, event: () -> Unit) {
        parentActivity?.let {
            val alert = AlertDialog.Builder(it)
                .setMessage(errorModel.cause)
                .setIcon(R.drawable.logout_24)
                .setPositiveButton("Accept") { p0, p1 ->
                    event()
                }
                .setOnDismissListener {
                    event()
                }
            alert.show()
        }
    }
}