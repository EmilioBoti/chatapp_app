package com.example.chatapp.views.ui.chatRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ActivityChatRoomBinding
import com.example.chatapp.helpers.OnLongClickItem
import com.example.chatapp.helpers.Session
import com.example.chatapp.viewModels.chat.ChatViewModel

class ChatRoom : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    private var bundle: Bundle? = null
    private val chatViewModel: ChatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        bundle = intent.getBundleExtra("data")
        chatViewModel.setUpSocket(bundle, this)


        chatViewModel.listMessages.observe(this, Observer {
            val messageAdapter = MessageAdapter(it, Session.getUserId(this.applicationContext)!!)
            messageAdapter.setLongListener(object : OnLongClickItem {
                override fun onLongClick(value: String) {
                    Toast.makeText(this@ChatRoom,  value, Toast.LENGTH_SHORT).show()
                }

            })

            binding.messageContainer.apply {
                layoutManager = LinearLayoutManager(this@ChatRoom, RecyclerView.VERTICAL, false)
                scrollToPosition(it.size - 1)
                setHasFixedSize(true)
                adapter = messageAdapter
            }
            binding.boxMessage.text.clear()
        })

        binding.btnSender.setOnClickListener {
            val text: String = binding.boxMessage.text.toString()
            if (text.isNotEmpty()) {
                chatViewModel.sendMessage(text)
            }
        }
    }

}