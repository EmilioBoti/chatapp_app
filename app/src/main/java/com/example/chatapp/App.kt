package com.example.chatapp

import android.app.Application
import android.content.SharedPreferences
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.example.chatapp.api.SocketCon
import com.example.chatapp.component.DaggerRepositoryComponent
import com.example.chatapp.component.RepositoryComponent
import com.example.chatapp.helpers.Session
import com.example.chatapp.module.RepositoryModule
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.googlecompat.GoogleCompatEmojiProvider
import dagger.internal.DaggerCollections

/**
 *  this class is used to initialize the socket client with the server to chat
 *  and some others dependencies like Dagger for dependency-injection
 *
 */
class App: Application() {
    private lateinit var repositoryComponent: RepositoryComponent


    companion object {
        var prefers: SharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()

        prefers = Session.getInstance(applicationContext)
        installEmoji()
        repositoryComponent = DaggerRepositoryComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .build()

    }

    fun getComponent() = repositoryComponent


    /**
     *  Install Emojis
     */
    private fun installEmoji() {
        EmojiManager.install(GoogleCompatEmojiProvider(EmojiCompat.init(
            FontRequestEmojiCompatConfig(
                this,
                FontRequest(
                    "com.google.android.gms.fonts",
                    "com.google.android.gms",
                    "Noto Color Emoji Compat",
                    R.array.com_google_android_gms_fonts_certs,
                )
            ).setReplaceAll(true)
        )
        ))
    }
}