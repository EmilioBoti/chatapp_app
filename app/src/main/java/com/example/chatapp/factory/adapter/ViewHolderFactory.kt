package com.example.chatapp.factory.adapter

import android.view.View
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.viewModels.home.UserViewHolder
import com.example.chatapp.viewModels.notifications.INotificationOnClick
import com.example.chatapp.views.ui.browser.adapter.BrowserHolder
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
            }
        }
    }
}