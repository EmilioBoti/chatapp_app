package com.example.chatapp.views.ui.browser.adapter

import android.view.View
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.factory.adapter.ModelViewHolder
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.UserModel

class RecentSearchVh(itemView: View,  private val listener: OnClickItem?): ModelViewHolder(itemView) {
    private val name: TextView? = itemView.findViewById(R.id.userName)

    override fun <T> bindData(value: T) {
        val user: UserModel = value as UserModel

        name?.text = formatName(user.name)

        itemView.setOnClickListener {
            listener?.onClick(absoluteAdapterPosition)
        }
    }

    private fun formatName(name: String): String {
        return if (name.length > 6) {
            name.replaceRange(6, name.length, "...")
        } else {
            name
        }
    }
}