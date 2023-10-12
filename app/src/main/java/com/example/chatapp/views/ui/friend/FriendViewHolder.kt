package com.example.chatapp.views.ui.friend

import android.view.View
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.factory.adapter.ModelViewHolder
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.UserModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendViewHolder (itemView: View, private val listener: OnClickItem?) : ModelViewHolder(itemView) {
    private val name: TextView? = itemView.findViewById(R.id.userName)
    private val image: CircleImageView? = itemView.findViewById(R.id.userImage)

    override fun <T> bindData(value: T) {
        val userModel = value as UserModel
        name?.text = userModel.name

        userModel.image_url?.let { imgUrl ->
            Picasso.get().load(imgUrl)
                .into(image)
        }

        itemView.setOnClickListener {
            listener?.onClick(absoluteAdapterPosition)
        }
    }
}