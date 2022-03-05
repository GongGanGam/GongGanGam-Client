package com.example.gonggangam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.databinding.ActivityAcceptDiaryBinding

class AcceptDiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAcceptDiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcceptDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.acceptDiaryBackIv.setOnClickListener {
            finish()
        }

        binding.acceptDiaryReplyBtn.setOnClickListener {
            val intent = Intent(this@AcceptDiaryActivity, ReplyToDiaryActivity::class.java)
            startActivity(intent)
        }
    }
}