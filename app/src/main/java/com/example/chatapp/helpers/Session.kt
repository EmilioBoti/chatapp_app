package com.example.chatapp.helpers

import android.content.Context
import com.example.chatapp.R
import com.example.chatapp.model.UserModel

class Session {
    companion object {
         const val ROOMID: String = "roomId"
         const val ID: String = "id"
         const val NAME: String = "name"
         const val EMAIL: String = "email"
         const val SOCKETID: String = "socketId"

        fun saveUser(context: Context, userModel: UserModel) {
           context.getSharedPreferences(context.getString(R.string.userData), Context.MODE_PRIVATE)
               .edit().apply {
                   putString(ID, userModel.id)
                   putString(NAME, userModel.name)
                   putString(EMAIL, userModel.email)
                   apply()
               }
            
        }

        fun getUserLogin(context: Context): Boolean? {
           val prefe = context.getSharedPreferences(context.getString(R.string.userData), Context.MODE_PRIVATE)
            return prefe?.contains(ID)
        }

        fun logout(context: Context): Boolean? {
           val prefe = context.getSharedPreferences(context.getString(R.string.userData), Context.MODE_PRIVATE).edit().clear()
            return prefe?.commit()
        }

        fun getUserId(context: Context): String? {
           val prefe = context.getSharedPreferences(context.getString(R.string.userData), Context.MODE_PRIVATE)
            return prefe?.getString(ID, "")
        }
    }
}