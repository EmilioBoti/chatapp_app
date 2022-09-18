package com.example.chatapp.views.ui.browser.adapter

import android.view.View
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.factory.adapter.ModelViewHolder
import com.example.chatapp.helpers.OnClickItem
import com.example.chatapp.repositoryApi.models.UserModel

class BrowserHolder(itemView: View, private val listener: OnClickItem?): ModelViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.userName)
    private val request: TextView = itemView.findViewById(R.id.request)

    override fun <T> bindData(value: T) {
        val user = value as UserModel
        name.text = user.name

        request.setOnClickListener {
            listener?.onClick(absoluteAdapterPosition)
        }

    }
}