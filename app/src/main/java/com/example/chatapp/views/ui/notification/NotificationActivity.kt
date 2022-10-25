package com.example.chatapp.views.ui.notification

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityNotificationBinding
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelAdapter
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.NotificationModel
import com.example.chatapp.viewModels.notifications.NotificationViewModel

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private val notificationViewModel: NotificationViewModel by viewModels()
    private var data: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        setToolbar()

        notificationViewModel.getNotification()
        notificationViewModel.listNotification.observe(this, Observer {
            showNotification(it)
        })

    }

    private fun showNotification(users: MutableList<NotificationModel>) {
        val notificationAdapter = ModelAdapter<NotificationModel>(users, FactoryBuilder.NOTIFICATION)
        notificationAdapter.setLayout(R.layout.notification_item)

        notificationAdapter.setListener(object : OnClickItem {

            override fun onAccept(notification: NotificationModel) {
                notificationViewModel.acceptNotification(notification)
            }

            override fun onReject(notification: NotificationModel) {
                rejectUser(notification)
            }

            override fun onClick(pos: Int) {

            }

        })

        binding.notificationContainer.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }
    }

    private fun rejectUser(notification: NotificationModel) {
        val alert = AlertDialog.Builder(this)
            .setTitle(R.string.logout)
            .setMessage(R.string.logoutMessage)
            .setIcon(R.drawable.logout_24)
            .setNegativeButton("Cancel") { p0, p1 ->

            }
            .setPositiveButton("Accept") { p0, p1 ->
                notificationViewModel.rejectNotification(notification)
            }
        alert.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setToolbar() {

        binding.toolbarNotification.title = "Notifications"
        binding.toolbarNotification.navigationIcon = ContextCompat.getDrawable(this, R.drawable.keyboard_backspace_24)
        binding.toolbarNotification.setNavigationOnClickListener {
            onBackPressed()
        }


    }
}