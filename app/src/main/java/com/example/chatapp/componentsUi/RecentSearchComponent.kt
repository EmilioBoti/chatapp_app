package com.example.chatapp.componentsUi

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.factory.adapter.FactoryBuilder
import com.example.chatapp.factory.adapter.ModelAdapter
import com.example.chatapp.helpers.common.OnClickItem
import com.example.chatapp.remoteRepository.models.UserModel

class RecentSearchComponent(context: Context, attrs: AttributeSet?): LinearLayoutCompat(context, attrs) {
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var recentUserContainer: RecyclerView
    private var recentUserList: MutableList<UserModel> = mutableListOf()
    private var userAdapter: ModelAdapter<UserModel>

    init {
        inflater.inflate(R.layout.recent_search_component, this, true)
        recentUserContainer = findViewById(R.id.recentUsersContainer)


        userAdapter = ModelAdapter<UserModel>(recentUserList, FactoryBuilder.RECENT_SEARCH)
        userAdapter.setLayout(R.layout.recent_search_user)

        recentUserContainer.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = userAdapter
        }
    }

    fun setListUser(recentUser: MutableList<UserModel>) {
        this.recentUserList = recentUser
    }

    fun setOnClick(listener: OnClickItem) {
        userAdapter.setListener(listener)
    }

}