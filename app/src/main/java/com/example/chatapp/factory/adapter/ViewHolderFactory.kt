package com.example.chatapp.factory.adapter

import android.view.View
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.views.home.adapter.UserViewHolder
import com.example.chatapp.views.ui.browser.adapter.BrowserHolder
import com.example.chatapp.views.ui.browser.adapter.RecentSearchVh
import com.example.chatapp.views.ui.friend.FriendViewHolder
import com.example.chatapp.views.ui.notification.NotificationViewHolder

class ViewHolderFactory {
    companion object {
        fun buildFactory(factoryBuilder: FactoryBuilder, itemView: View, onClickItem: OnClickItem?): ModelViewHolder {
            return when(factoryBuilder) {
                FactoryBuilder.SEARCH -> {
                    BrowserHolder(itemView, onClickItem)
                }
                FactoryBuilder.CONTACT -> {
                    UserViewHolder(itemView, onClickItem)
                }
                FactoryBuilder.NOTIFICATION -> {
                    NotificationViewHolder(itemView, onClickItem)
                }
                FactoryBuilder.RECENT_SEARCH -> {
                    RecentSearchVh(itemView, onClickItem)
                }
                FactoryBuilder.FRIEND -> {
                    FriendViewHolder(itemView, onClickItem)
                }
            }
        }
    }
}