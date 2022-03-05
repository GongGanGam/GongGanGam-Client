package com.example.gonggangam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.databinding.ActivityMyPageLeaveBinding

class MyPageLeaveActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPageLeaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageLeaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mypageLeaveXIv.setOnClickListener {
            finish()
        }

        binding.mypageLeaveCancelBtn.setOnClickListener {
            finish()
        }

        binding.mypageLeaveWithdrawalBtn.setOnClickListener {

        }

    }
}
