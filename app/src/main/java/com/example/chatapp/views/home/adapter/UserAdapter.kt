package com.example.chatapp.views.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.views.home.adapter.UserViewHolder

class UserAdapter(private val listContacts: MutableList<UserModel>, private val listener: OnClickItem?): RecyclerView.Adapter<UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, null)
        return UserViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //holder.binData(listContacts[position])
    }

    override fun getItemCount(): Int = listContacts.size
}