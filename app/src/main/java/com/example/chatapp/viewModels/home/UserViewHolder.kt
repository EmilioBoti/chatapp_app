package com.example.chatapp.viewModels.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.helpers.OnClickItem
import com.example.chatapp.model.UserModel

class UserViewHolder(itemView: View, private val listener: OnClickItem?) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.userName)

    fun binData(userModel: UserModel) {
        name.text = userModel.name

        itemView.setOnClickListener {
            listener?.onClick(absoluteAdapterPosition)
        }
    }
}