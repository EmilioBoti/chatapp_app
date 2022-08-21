package com.example.chatapp.viewModels.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.factory.ModelViewHolder
import com.example.chatapp.helpers.OnClickItem
import com.example.chatapp.model.UserModel

class UserViewHolder(itemView: View, private val listener: OnClickItem?) : ModelViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.userName)
    private val lastMessage: TextView = itemView.findViewById(R.id.lastMessage)

    fun binData(userModel: UserModel) {

    }

    override fun <T> bindData(value: T) {
        val userModel = value as UserModel
        name.text = userModel.name
        lastMessage.text = userModel.lastMessage

        itemView.setOnClickListener {
            listener?.onClick(absoluteAdapterPosition)
        }
    }
}