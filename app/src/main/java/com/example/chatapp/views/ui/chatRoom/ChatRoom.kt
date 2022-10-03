package com.example.chatapp.views.ui.chatRoom

import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityChatRoomBinding
import com.example.chatapp.helpers.common.OnLongClickItem
import com.example.chatapp.helpers.Session
import com.example.chatapp.viewModels.chat.ChatViewModel
import com.vanniktech.emoji.EmojiPopup

class ChatRoom : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    private var bundle: Bundle? = null
    private val chatViewModel: ChatViewModel by viewModels()
    private val DATA: String = "data"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        bundle = intent.getBundleExtra(DATA)
        chatViewModel.getMessages(bundle)

        setToolbar()

        chatViewModel.listMessages.observe(this, Observer { messages ->

            Session.getUserId(this.applicationContext)?.let { userId ->

                val messageAdapter = MessageAdapter(messages, userId)
                messageAdapter.setLongListener(object : OnLongClickItem {
                    override fun onLongClick(value: String) {
                        //Toast.makeText(this@ChatRoom,  value, Toast.LENGTH_SHORT).show()
                    }

                })

                binding.messageContainer.apply {
                    layoutManager = LinearLayoutManager(this@ChatRoom, RecyclerView.VERTICAL, false)
                    scrollToPosition(messages.size - 1)
                    setHasFixedSize(true)
                    adapter = messageAdapter
                }

            }
        })

        val emojiPopup = EmojiPopup(binding.rootView, binding.inputContainer.getEmojiEditText())
        binding.inputContainer.setViewModel(chatViewModel)
        binding.inputContainer.setEmojiPopup(emojiPopup)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setToolbar() {

//        window.apply {
//            this.statusBarColor = ContextCompat.getColor(this@ChatRoom, R.color.purple_500)
//        }

        bundle?.let {
            binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.keyboard_backspace_24)
            binding.toolbar.setNavigationOnClickListener {
                this.onBackPressed()
            }
            binding.toolbar.menu.findItem(R.id.call)
                .iconTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding.toolbar.title = it.getString(Session.NAME)
        }

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.call -> {
                    Toast.makeText(this, "Call", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }
}