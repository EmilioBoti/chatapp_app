package com.example.chatapp.remoteRepository.models

data class ApiError(
    val error_id: String,
    val cause: String,
    val status: Int
)