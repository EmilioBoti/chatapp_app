package com.example.chatapp.helpers.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Utils {
    companion object {

        fun getRetrofitBuilder(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Const.HOST_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}