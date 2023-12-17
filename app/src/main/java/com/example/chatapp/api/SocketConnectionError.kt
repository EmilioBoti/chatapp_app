package com.example.chatapp.api

import com.example.chatapp.helpers.enums.TypeError

interface SocketConnectionError {

    fun connectionError(typeSocketError: TypeError)
}