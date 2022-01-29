package com.example.GongGanGam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets.add
import android.graphics.Insets
import android.view.Gravity.apply
import androidx.core.view.GravityCompat.apply
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.GongGanGam.R
import com.example.GongGanGam.databinding.ActivityMyPageNoticeBinding
import com.example.GongGanGam.Notice

class MyPageNoticeActivity:AppCompatActivity() {
    lateinit var binding: ActivityMyPageNoticeBinding
    private lateinit var noticeDatas: List<Notice>;
    private lateinit var adapter: MypageNoticeRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.mypage_notice_recyclerView)

        noticeDatas = ArrayList()
        noticeDatas = loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MypageNoticeRVAdapter(noticeDatas)
        recyclerView.adapter = adapter

        binding.mypageNoticeBackIv.setOnClickListener {
            finish()
        }
    }

    private fun loadData(): List<Notice> {
        val noticelist = ArrayList<Notice>()

        val list=Notice().apply {
            title= "안녕하세요"
            date="20.10.02"
        }
        noticelist.add(list)

        val list02=Notice().apply {
            title= "안녕하세요020202020202"
            date="25.10.05"
        }
        noticelist.add(list02)

        return noticelist
    }
}