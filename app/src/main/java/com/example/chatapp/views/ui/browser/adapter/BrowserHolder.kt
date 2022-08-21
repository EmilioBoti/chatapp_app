package com.example.chatapp.views.ui.browser.adapter

import android.view.View
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.factory.ModelViewHolder
import com.example.chatapp.model.UserModel

class BrowserHolder(itemView: View): ModelViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.userName)

    override fun <T> bindData(value: T) {
        val user = value as UserModel
        name.text = user.name
    }
}