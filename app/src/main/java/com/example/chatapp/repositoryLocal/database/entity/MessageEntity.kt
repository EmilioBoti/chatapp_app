package com.example.chatapp.repositoryLocal.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.chatapp.remoteRepository.models.MessageModel

@Entity(tableName = "message_table")
data class MessageEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "room_id") val roomId: String,
    @ColumnInfo(name = "from_user") val fromUser: String,
    @ColumnInfo(name = "to_user") val toUser: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "time") val time: String,
)

fun MessageEntity.convertToMessageModel(): MessageModel = MessageModel(id, roomId, fromUser, toUser, userName, message, time)