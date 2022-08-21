package com.example.chatapp.factory

import android.view.View
import com.example.chatapp.viewModels.home.UserViewHolder
import com.example.chatapp.views.ui.browser.adapter.BrowserHolder

class ViewHolderFactory {
    companion object {
        fun buildFactory(factoryBuilder: FactoryBuilder, itemView: View): ModelViewHolder {
            return when(factoryBuilder) {
                FactoryBuilder.SEARCH -> {
                    BrowserHolder(itemView)
                }
                FactoryBuilder.CONTACT -> {
                    UserViewHolder(itemView, null)
                }
            }
        }
    }
}