package com.example.chatapp.factory.adapter

import android.view.View
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelViewHolder
import com.example.chatapp.helpers.OnClickItem
import com.example.chatapp.viewModels.home.UserViewHolder
import com.example.chatapp.views.ui.browser.adapter.BrowserHolder

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
            }
        }
    }
}