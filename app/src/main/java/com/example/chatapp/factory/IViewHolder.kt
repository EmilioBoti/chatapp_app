package com.example.chatapp.factory

interface IViewHolder<in T>{
    fun bindData(value: T)
}