package com.example.gonggangam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityMyPageNoticeBinding

class MyPageNoticeActivity:AppCompatActivity() {
    lateinit var binding: ActivityMyPageNoticeBinding
    private lateinit var noticeDatas: ArrayList<Notice>;
    private lateinit var adapter: MypageNoticeRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.mypage_notice_recyclerView)

        noticeDatas = ArrayList()
        noticeDatas.apply{
            add(Notice("공간감 공지사항","22.04.23", "binding = ActivityMyPageNoticeBinding.inflate(layoutInflater)ice_recyclerView)\n\nval recyclerVibinding = ActivityMyPageNoticeBinding.inflate(layoutInflater)eecbinding = ActivityMyPageNoticeBinding.inflate(layoutal \n\nrecbinding = ActivityMyPageNoticeBinding.inflate(layoutInflater)"))
            add(Notice("공간감 공지사항02","21.03.14","eqwdqwdsqw"))
            add(Notice("공간감 공지사항03","21.01.31","e"))
            add(Notice("공간감 공지사항04","21.12.12","ateinit var binding: ActivityMyPageNoticeBinding"))
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MypageNoticeRVAdapter(noticeDatas)
        recyclerView.adapter = adapter

        binding.mypageNoticeBackIv.setOnClickListener {
            finish()
        }
    }

}