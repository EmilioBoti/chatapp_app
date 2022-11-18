package com.example.chatapp.helpers.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class Utils @Inject constructor() {

    companion object {
        private const val startRange: Int = 0
        private const val endRange: Int = 18

        fun getRetrofitBuilder(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Const.HOST_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun formatDate(date: String): String {
            val format = SimpleDateFormat("HH:mm.a", Locale.getDefault())
            val timestamp = Timestamp.valueOf(date.replace("T", " ").slice(IntRange(startRange, endRange)))
            return format.format(Date(timestamp.time)).lowercase()
        }
    }

    fun getRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.HOST_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}