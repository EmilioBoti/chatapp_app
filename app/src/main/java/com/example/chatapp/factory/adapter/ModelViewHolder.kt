package com.example.chatapp.factory.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ModelViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun <T> bindData(value: T)
}