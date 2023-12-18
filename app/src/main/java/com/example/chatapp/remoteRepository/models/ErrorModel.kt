package com.example.chatapp.remoteRepository.models

import com.example.chatapp.helpers.enums.TypeError

data class ErrorModel(
    val type: TypeError = TypeError.UNKNOWN,
    val cause: String,
    val errorId: String,
    val status: Int
    )

fun ApiError.convertToErrorModel(type: TypeError): ErrorModel {
    return ErrorModel(
        type,
        this.cause,
        this.error_id,
        this.status
    )
}