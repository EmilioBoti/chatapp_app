package com.example.chatapp.views.ui.notification

import android.view.View
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.factory.adapter.ModelViewHolder
import com.example.chatapp.repositoryApi.models.UserModel

class NotificationAdapter(itemView: View): ModelViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.userName)

    override fun <T> bindData(value: T) {
        val user: UserModel = value as UserModel
    }
}