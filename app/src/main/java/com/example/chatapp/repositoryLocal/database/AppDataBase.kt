package com.example.chatapp.repositoryLocal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatapp.repositoryLocal.database.dao.ChatDao
import com.example.chatapp.repositoryLocal.database.entity.MessageEntity
import com.example.chatapp.repositoryLocal.database.entity.UserEntity

@Database(entities = [UserEntity::class, MessageEntity::class],
    version = 2
    )
abstract class AppDataBase: RoomDatabase() {
    abstract fun getChatDao(): ChatDao
}