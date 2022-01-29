package com.example.GongGanGam

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.GongGanGam.databinding.ActivityDiaryWriteEmojiBinding

class DiaryWriteEmojiActivity : AppCompatActivity() {
    lateinit var binding:ActivityDiaryWriteEmojiBinding

    private var emojiList = arrayListOf<Emoji>(
        Emoji(R.drawable.profile_img_camera, "완전 행복해요"),
        Emoji(R.drawable.profile_img_camera, "매우 즐거워욧"),
        Emoji(R.drawable.x_btn, "그냥 그래요"),
        Emoji(R.drawable.profile_img_camera, "지루해요"),
        Emoji(R.drawable.profile_img_camera, "너무 슬퍼요"),
        Emoji(R.drawable.profile_img_camera, "매우 화나요"),
        Emoji(R.drawable.arrow_down, "진짜 짜증나요"),
        Emoji(R.drawable.profile_img_camera, "많이 우울해요"),
        Emoji(R.drawable.profile_img_camera, "창피해요"),
        Emoji(R.drawable.profile_img_camera, "설레요"),
        Emoji(R.drawable.profile_img_camera, "복잡해요"),
        Emoji(R.drawable.profile_img_camera, "궁금해요"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryWriteEmojiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.diary_write_emoji_recyclerView)
        val Adapter = DiaryWriteEmojiRVAdapter(this.emojiList)
        recyclerView.adapter =Adapter


        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        recyclerView.layoutManager=gridLayoutManager
    }


}