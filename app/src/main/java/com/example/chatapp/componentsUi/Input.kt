package com.example.chatapp.componentsUi

import android.content.Context
import android.content.res.TypedArray
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.example.chatapp.R
import com.example.chatapp.databinding.InputBinding
import com.example.chatapp.viewModels.login.InputMethod
import com.google.android.material.textfield.TextInputEditText

class Input(context: Context, attrs: AttributeSet?): LinearLayoutCompat(context, attrs), InputMethod {
    private var binding: InputBinding
    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var attributes: TypedArray


    init {
        binding = InputBinding.inflate(inflater, this, true)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.Input)
        setUp()
    }

    override fun setUp() {
        val type: TypeInput = TypeInput.values()[attributes.getInt(R.styleable.Input_typeInput, 0)]
        binding.inputContainer.hint = attributes.getString(R.styleable.Input_hintInput)
        setIcon(attributes.getResourceId(R.styleable.Input_icon, 0), null)
        setInputType(type)
    }

    override fun setIcon(icon: Int, iconColor: Int?) {
        binding.inputContainer.setStartIconDrawable(icon)
        iconColor?.let {
            binding.inputContainer.setStartIconTintList(ContextCompat.getColorStateList(context, iconColor))
        }
    }

    override fun setInputType(type: TypeInput) {
        when(type) {
            TypeInput.TEXT -> {}
            TypeInput.PASSWORD -> {
                binding.input.run {
                    transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
        }
    }

    fun getEditInput() : TextInputEditText = binding.input

}