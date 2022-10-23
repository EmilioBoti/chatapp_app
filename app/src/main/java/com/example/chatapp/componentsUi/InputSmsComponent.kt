package com.example.chatapp.componentsUi

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.chatapp.R
import com.vanniktech.emoji.EmojiEditText


class InputSmsComponent(context: Context, attrs: AttributeSet?): RelativeLayout(context, attrs) {
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var btnSender: ImageView
    private var btnEmoji: ImageView
    private var inputText: EmojiEditText
    private var attribute: TypedArray

    init {
        inflater.inflate(R.layout.input_component, this, true)

        inputText = findViewById(R.id.boxMessage)
        btnSender = findViewById(R.id.btnSender)
        btnEmoji = findViewById(R.id.btEmoji)
        inputText.showSoftInputOnFocus = true

        attribute = context.obtainStyledAttributes(attrs, R.styleable.InputSmsComponent)

        val iconSender = attribute.getResourceId(R.styleable.InputSmsComponent_icon_btnSender, 0)
        val iconEmoji = attribute.getResourceId(R.styleable.InputSmsComponent_icon_btnEmoji, 0)

        setIconBtnSender(iconSender)
        setIconEmojis(iconEmoji)

    }

    fun setSenderOnClick(listener: OnClickListener) {
        btnSender.setOnClickListener(listener)
    }

    fun setEmojiOnClick(listener: OnClickListener) {
        btnEmoji.setOnClickListener(listener)
    }

    fun setIconEmojis(refId: Int) {
        btnEmoji.setImageResource(refId)
    }

    fun setIconBtnSender(refId: Int) {
        btnSender.setImageResource(refId)
    }

    fun getEmojiEditText() : EmojiEditText = inputText

}