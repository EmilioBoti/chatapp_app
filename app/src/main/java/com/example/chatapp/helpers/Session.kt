package com.example.chatapp.helpers

import android.content.Context
import android.content.SharedPreferences
import com.example.chatapp.R
import com.example.chatapp.remoteRepository.models.UserModel
import com.example.chatapp.remoteRepository.models.auth.AuthApiResponse

class Session {
    companion object {
         const val ROOMID: String = "roomId"
         const val ID: String = "id"
         const val NAME: String = "name"
         const val EMAIL: String = "email"
         const val SOCKETID: String = "socketId"
         const val TOKEN: String = "token"
        private var prefes: SharedPreferences? = null

        fun getInstance(context: Context): SharedPreferences?  {
            prefes = context.getSharedPreferences(context.getString(R.string.userDataToken), Context.MODE_PRIVATE)
            return prefes
        }

        fun saveToken(context: Context, token: String) {
            prefes?.edit()?.apply {
                    putString(TOKEN, token)
                    apply()
                }
        }

        fun getToken(): String {
            return prefes?.getString(TOKEN, "") ?: ""
        }

        fun saveUser(context: Context, userModel: UserModel) {
           prefes?.edit()?.apply {
                   putString(ID, userModel.id)
                   putString(NAME, userModel.name)
                   putString(EMAIL, userModel.email)
                   apply()
               }
            
        }

        fun saveUserSession(user: AuthApiResponse) {
            prefes?.edit()?.apply {
                putString(ID, user.id)
                putString(NAME, user.name)
                putString(EMAIL, user.email)
                putString(TOKEN, user.token)
                apply()
            }
        }

        fun getSession(): Map<String, *>? {
            return prefes?.all
        }

        fun getUserLogin(context: Context): Boolean? {
           val prefe = context.getSharedPreferences(context.getString(R.string.userData), Context.MODE_PRIVATE)
            return prefe?.contains(ID)
        }

        fun isLogin(context: Context): Boolean? {
           val prefe = context.getSharedPreferences(context.getString(R.string.userDataToken), Context.MODE_PRIVATE)
            return prefe?.contains(TOKEN)
        }

        fun logout(context: Context): Boolean? {
           val prefe = context.getSharedPreferences(context.getString(R.string.userDataToken), Context.MODE_PRIVATE).edit().clear()
            return prefe?.commit()
        }

        fun getUserId(): String? {
            return prefes?.getString(ID, "")
        }
    }
}