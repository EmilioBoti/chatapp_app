package com.example.chatapp.repositoryLocal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.repositoryLocal.database.entity.MessageEntity
import com.example.chatapp.repositoryLocal.database.entity.UserEntity
import javax.inject.Inject

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUser(list: MutableList<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMessage(listMessages: MutableList<MessageEntity>)

    @Query("select message_table.id as id,message_table.room_id, message_table.from_user," +
            " message_table.to_user, message_table.message, message_table.user_name," +
            " message_table.time as time " +
            "from message_table " +
            "inner join user_table on message_table.room_id = user_table.room_id" +
            " where user_table.room_id = :roomId"
    )
    suspend fun getRoomMessages(roomId: String): MutableList<MessageEntity>

    @Query("select user_table.name , message_table.room_id,\n" +
            "user_table.id, user_table.to_user, message_table.time, user_table.email\n" +
            ", (select message from message_table\n" +
            "              where message_table.room_id = user_table.room_id\n" +
            "              order by message_table.time desc\n" +
            "              limit 1\n" +
            "              ) as lastmessage\n" +
            "from user_table\n" +
            "left join message_table on user_table.id = message_table.to_user\n" +
            "where user_table.to_user = :userId" +
            " group by user_table.email ")
    suspend fun getAllContacts(userId: String): MutableList<UserEntity>
}