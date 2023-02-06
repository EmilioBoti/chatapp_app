package com.example.chatapp.repositoryLocal.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chatapp.remoteRepository.models.UserModel

@Entity(tableName = "user_table")
data class UserEntity(
     @PrimaryKey @ColumnInfo(name = "id") val id: String,
     @ColumnInfo(name = "name") val name: String,
     @ColumnInfo(name = "email") val email: String,
     @ColumnInfo(name = "room_id") val roomId: String,
     @ColumnInfo(name = "to_user") val toUser: String
)

fun UserEntity.convertToUserModel() : UserModel = UserModel(roomId, id, name, email, null, null, toUser, null, null, null)