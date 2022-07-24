package com.example.gonggangam.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivitySplashBinding
import com.example.gonggangam.util.PrefManager

class SplashActivity: AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            when(PrefManager.jwt.isBlank()) {
                true -> { // 회원 가입 이전
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                false -> { // 회원 가입 후 자동 로그인
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, getString(R.string.auto_login_toast_message), Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        },2000) //1초가 1000mills
    }

    private fun startNextActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}