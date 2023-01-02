package com.example.chatapp.views.ui.notification

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

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
        notificationAdapter.setLayout(R.layout.notification_item_2)

        notificationAdapter.setListener(object : OnClickItem {

            override fun onAccept(notification: NotificationModel, view: View, position: Int) {
                notificationViewModel.acceptNotification(notification, position)
            }

            override fun onReject(notification: NotificationModel, view: View, position: Int) {
                rejectUser(notification, position)
            }

            override fun onClick(pos: Int) {

            }

        })

        binding.notificationContainer.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }
    }

    private fun rejectUser(notification: NotificationModel, position: Int) {
        val alert = AlertDialog.Builder(this)
            .setTitle(R.string.removeNotification)
            .setMessage(R.string.removeNotificationMessage)
            .setNegativeButton("Cancel") { p0, p1 ->

            }
            .setPositiveButton("Accept") { p0, p1 ->
                notificationViewModel.rejectNotification(notification, position)
            }
        alert.show()
    }

    private fun setToolbar() {

        binding.toolbarNotification.title = "Notifications"
        binding.toolbarNotification.navigationIcon = ContextCompat.getDrawable(this, R.drawable.keyboard_backspace_24)
        binding.toolbarNotification.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}