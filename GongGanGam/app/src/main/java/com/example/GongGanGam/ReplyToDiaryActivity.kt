package com.example.GongGanGam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.example.GongGanGam.databinding.ActivityReplyToDiaryBinding

class ReplyToDiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityReplyToDiaryBinding
    private var readyToReply : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReplyToDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.replyToDiaryContentEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                readyToReply = binding.replyToDiaryContentEt.text.toString().isNotEmpty()
                if(readyToReply) {
                    binding.replyToDiaryBtn.setBackgroundResource(R.drawable.button_active_background)
                    binding.replyToDiaryBtnTv.setTextColor(resources.getColor(R.color.primaryColor))
                }
                else {
                    binding.replyToDiaryBtn.setBackgroundResource(R.drawable.button_inactive_background)
                    binding.replyToDiaryBtnTv.setTextColor(resources.getColor(R.color.inactiveBtnColor))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.replyToDiaryBackIv.setOnClickListener {
            finish()
        }
    }
}