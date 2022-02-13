package com.example.gonggangam

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import com.example.gonggangam.databinding.ActivityReplyToDiaryBinding

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
        binding.replyToDiaryBackCl.setOnClickListener {
            hideKeyBoard()
        }

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

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.replyToDiaryContentEt.windowToken, 0)
        binding.replyToDiaryContentEt.clearFocus()
    }
}