package com.example.chatapp.views.ui.chatRoom

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.App
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityChatRoomBinding
import com.example.chatapp.helpers.common.OnLongClickItem
import com.example.chatapp.helpers.utils.Const
import com.example.chatapp.remoteRepository.models.MessageModel
import com.example.chatapp.viewModels.chat.ChatViewModel
import com.example.chatapp.viewModels.chat.useCase.ChatUseCase
import com.vanniktech.emoji.EmojiPopup
import javax.inject.Inject

class ChatRoom : AppCompatActivity() {
    private lateinit var binding: ActivityChatRoomBinding
    private var bundle: Bundle? = null
    private val DATA: String = "data"

    @Inject
    lateinit var chatUseCase: ChatUseCase

    private val chatViewModel: ChatViewModel by viewModels {
        ChatViewModel.provideFactory(chatUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        (this.application as App).getComponent().inject(this)
        bundle = intent.getBundleExtra(DATA)
        chatViewModel.setUp(bundle)
        setToolbar()
        setEmoji()

        chatViewModel.listMessages.observe(this, Observer { messages ->
            setSmsAdapter(messages)
        })

    }

    private fun setToolbar() {

        bundle?.let {
            binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.keyboard_backspace_24)
            binding.toolbar.setNavigationOnClickListener {
                this.onBackPressed()
            }
            binding.toolbar.menu.findItem(R.id.call)
                .iconTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            binding.toolbar.title = it.getString(Const.NAME_USER)
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

    private fun setEmoji() {
        val emojiPopup = EmojiPopup(binding.rootView, binding.inputContainer.getEmojiEditText())
        binding.inputContainer.run {
            setIconBtnSender(R.drawable.send_24)
            setIconEmojis(R.drawable.ic_emoji)
            setSenderOnClick {
                val text: String = getEmojiEditText().text.toString()
                if (text.isNotEmpty()) {
                    chatViewModel.sendMessage(text)
                    getEmojiEditText().text?.clear()
                }
            }
            setEmojiOnClick {
                emojiPopup.toggle()
            }
        }
    }

    private fun setSmsAdapter(messages: MutableList<MessageModel>) {
        val messageAdapter = MessageAdapter(messages, chatViewModel.currentUserId)
        messageAdapter.setLongListener(object : OnLongClickItem {
            override fun onLongClick(value: String) {
            }

        })

        binding.messageContainer.apply {
            layoutManager = LinearLayoutManager(this@ChatRoom, RecyclerView.VERTICAL, false)
            scrollToPosition(messages.size - 1)
            setHasFixedSize(true)
            adapter = messageAdapter
        }
    }
}