package com.example.GongGanGam.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.databinding.ActivityDiaryReadBinding

class DiaryReadActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.diaryReadHeader.layoutHeaderBackIv.setOnClickListener {
            finish()
        }

        //헤더 내의 내용 수정
        binding.diaryReadHeader.layoutHeaderBtnTv.text= "수정"
        binding.diaryReadHeader.layoutHeaderMenuIv.visibility= View.INVISIBLE
        binding.diaryReadHeader.layoutHeaderTitleTv.visibility= View.INVISIBLE

        binding.diaryReadHeader.layoutHeaderBackIv.setOnClickListener {
            finish()
        }

        binding.diaryReadMyCl.setOnClickListener {
            val intent = Intent(this, AcceptDiaryActivity::class.java)
            startActivity(intent)
        }
    }
    }