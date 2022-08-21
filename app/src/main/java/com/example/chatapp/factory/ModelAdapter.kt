package com.example.chatapp.factory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.views.ui.browser.adapter.BrowserHolder

class ModelAdapter<in T>(private val list: MutableList<T>): RecyclerView.Adapter<ModelViewHolder>() {
    private var layout: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, null)
        return ViewHolderFactory.buildFactory(FactoryBuilder.CONTACT, view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setLayout(layout: Int) {
        this.layout = layout
    }

}