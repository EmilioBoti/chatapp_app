package com.example.chatapp.views.ui.chatRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.chatapp.databinding.ActivityChatRoomBinding
import com.example.chatapp.viewModels.chat.ChatViewModel

class ChatRoom : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    private var bundle: Bundle? = null
    val chatViewModel: ChatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        bundle = intent.getBundleExtra("data")
        chatViewModel.setUpSocket(bundle, this)


        binding.btnSender.setOnClickListener {
            val text: String = binding.boxMessage.text.toString()
            if (text.isNotEmpty()) {
                chatViewModel.sendMessage(text)
            }
        }
    }

}