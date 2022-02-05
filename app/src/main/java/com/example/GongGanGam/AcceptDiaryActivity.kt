package com.example.GongGanGam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.GongGanGam.databinding.ActivityAcceptDiaryBinding
import com.example.GongGanGam.databinding.ActivityMainBinding

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