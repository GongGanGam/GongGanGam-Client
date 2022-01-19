package com.example.GongGanGam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import com.example.GongGanGam.databinding.ActivityAcceptChattingBinding

class AcceptChattingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAcceptChattingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcceptChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.acceptChattingBackIv.setOnClickListener {
            finish()
        }

        binding.acceptChattingRejectBtn.setOnClickListener {
            // 거절 누르고 -> 거절 상태 저장
            binding.acceptChattingFooter.visibility = View.GONE
        }

        binding.acceptChattingStartBtn.setOnClickListener {
            val intent = Intent(this@AcceptChattingActivity, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}