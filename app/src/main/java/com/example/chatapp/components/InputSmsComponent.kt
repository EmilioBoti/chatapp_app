package com.example.chatapp.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.chatapp.R
import com.example.chatapp.databinding.InputComponentBinding
import com.example.chatapp.viewModels.chat.ChatViewModel
import com.example.chatapp.viewModels.chat.IChat
import com.vanniktech.emoji.EmojiEditText
import com.vanniktech.emoji.EmojiPopup


class InputSmsComponent(context: Context, attr: AttributeSet?): RelativeLayout(context, attr) {
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var btnSender: ImageView
    private var btnEmoji: ImageView
    private var inputText: EmojiEditText
    private var chatViewModel: IChat.Presenter? = null
    private lateinit var emojiPopup : EmojiPopup

    init {
        inflater.inflate(R.layout.input_component, this, true)

        inputText = findViewById(R.id.boxMessage)
        btnSender = findViewById(R.id.btnSender)
        btnEmoji = findViewById(R.id.btEmoji)

        setClickListener()

    }

    private fun setClickListener() {
        btnSender.setOnClickListener {
            val text: String = inputText.text.toString()
            if (text.isNotEmpty()) {
                chatViewModel?.sendMessage(text)
                inputText.text?.clear()
            }
        }

        btnEmoji.setOnClickListener {
            emojiPopup.toggle()
        }
    }

    fun getEmojiEditText() : EmojiEditText = inputText

    fun setViewModel(chatViewModel: IChat.Presenter?) {
        this.chatViewModel = chatViewModel
    }

    fun setEmojiPopup(emojiPopup: EmojiPopup) {
        this.emojiPopup = emojiPopup
    }

}