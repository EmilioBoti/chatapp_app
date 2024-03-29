package com.example.chatapp.views.ui.chatRoom

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.helpers.common.OnLongClickItem
import com.example.chatapp.helpers.utils.Utils
import com.example.chatapp.remoteRepository.models.MessageModel

class MessageViewHolder(itemView: View,private val longListener: OnLongClickItem?): RecyclerView.ViewHolder(itemView) {
    private val message: TextView? = itemView.findViewById(R.id.messageText)
    private val time: TextView? = itemView.findViewById(R.id.time)

    fun bindData(message: MessageModel) {
        this.message?.text = message.message
        this.time?.text = Utils.formatDate(message.times)

        itemView.setOnLongClickListener{
            longListener?.onLongClick(message.messageId)
            true
        }
    }
}