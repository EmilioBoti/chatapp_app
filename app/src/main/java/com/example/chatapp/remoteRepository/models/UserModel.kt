package com.example.chatapp.remoteRepository.models

import com.example.chatapp.repositoryLocal.database.entity.UserEntity
import java.time.Instant
import java.util.Date

data class UserModel(
    val roomId:String,
    val id: String,
    val name: String,
    val email: String,
    val socketId: String?,
    var lastMessage: String?,
    val toUser: String,
    val state: Boolean?,
    var times: String?,
    val image_url: String?
    ): Comparable<UserModel> {

    override fun compareTo(other: UserModel): Int {
        val d1 = Date.from(Instant.parse(times))
        val d2 = Date.from(Instant.parse(other.times))

        val isOlder = d1.compareTo(d2)

        return when {
            isOlder > 0 -> -1
            isOlder < 0 -> 1
            else -> 0
        }
    }
}


fun UserModel.convertToUserEntity(): UserEntity = UserEntity(id, name, email, roomId, toUser)