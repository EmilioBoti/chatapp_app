package com.example.chatapp.views.ui.chatRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.helpers.common.OnLongClickItem
import com.example.chatapp.remoteRepository.models.MessageModel

class MessageAdapter(private val listMessage: MutableList<MessageModel>, private val from: String,
                     ): RecyclerView.Adapter<MessageViewHolder>() {
    private val typeOne: Int = 0
    private val typeTwo: Int = 1
    private lateinit var view: View
    private var longListener: OnLongClickItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        return when (viewType) {
            typeOne -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_right_1, null)
                MessageViewHolder(view, longListener)
            }
            typeTwo -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.message_item_left_2, null)
                MessageViewHolder(view, longListener)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, null)
                MessageViewHolder(view, longListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if (listMessage[position].fromU == from) {
            typeOne
        } else {
            typeTwo
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindData(listMessage[position])
    }

    override fun getItemCount(): Int = listMessage.size

    fun setLongListener(longListener: OnLongClickItem?) {
        this.longListener = longListener
    }
}