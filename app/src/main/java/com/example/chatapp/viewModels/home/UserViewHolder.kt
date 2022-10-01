package com.example.chatapp.viewModels.home

import android.view.View
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.factory.adapter.ModelViewHolder
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.repositoryApi.models.UserModel

class UserViewHolder(itemView: View, private val listener: OnClickItem?) : ModelViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.userName)
    private val lastMessage: TextView = itemView.findViewById(R.id.lastMessage)
    private val time: TextView = itemView.findViewById(R.id.time)

    override fun <T> bindData(value: T) {
        val userModel = value as UserModel
        name.text = userModel.name
        lastMessage.text = userModel.lastMessage
        time.text = userModel.times

        itemView.setOnClickListener {
            listener?.onClick(absoluteAdapterPosition)
        }
    }
}