package com.example.chatapp.factory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.helpers.common.OnClickItem

class ModelAdapter<in T>(private var list: MutableList<T>, private val factoryBuilder: FactoryBuilder): RecyclerView.Adapter<ModelViewHolder>() {
    private var layout: Int = 0
    private var onClickItem: OnClickItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, null)
        return ViewHolderFactory.buildFactory(factoryBuilder, view, onClickItem)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setLayout(layout: Int) {
        this.layout = layout
    }

    fun setListener(onClickItem: OnClickItem?) {
        this.onClickItem = onClickItem
    }

}