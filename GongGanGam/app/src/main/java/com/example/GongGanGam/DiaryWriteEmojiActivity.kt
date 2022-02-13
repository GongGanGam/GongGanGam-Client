package com.example.GongGanGam

import android.os.Bundle
import android.graphics.Insets.add
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets.add
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.GongGanGam.databinding.ActivityDiaryWriteEmojiBinding
import kotlinx.android.synthetic.main.activity_diary_write_emoji.*

class DiaryWriteEmojiActivity : AppCompatActivity() {
    lateinit var binding:ActivityDiaryWriteEmojiBinding
    private lateinit var emojiDatas: ArrayList<Emoji>;
    private lateinit var adapter: DiaryWriteEmojiRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryWriteEmojiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emojiDatas =ArrayList()
        emojiDatas.apply{
            add(Emoji(R.drawable.profile_img_camera, "완전 행복해요"))
            add(Emoji(R.drawable.profile_img_camera, "매우 즐거워욧"))
            add(Emoji(R.drawable.profile_img_camera, "그냥 그래요"))
            add(Emoji(R.drawable.profile_img_camera, "지루해요"))
            add(Emoji(R.drawable.profile_img_camera, "너무 슬퍼요"))
            add(Emoji(R.drawable.profile_img_camera, "매우 화나요"))
            add(Emoji(R.drawable.profile_img_camera, "진짜 짜증나요"))
            add(Emoji(R.drawable.profile_img_camera, "많이 우울해요"))
            add(Emoji(R.drawable.profile_img_camera, "창피해요"))
            add(Emoji(R.drawable.profile_img_camera, "설레요"))
            add(Emoji(R.drawable.profile_img_camera, "복잡해요"))
            add(Emoji(R.drawable.profile_img_camera, "궁금해요"))
        }

        val emojiAdapter = DiaryWriteEmojiRVAdapter(emojiDatas)
        binding.diaryWriteEmojiRecyclerView.adapter = emojiAdapter


        emojiAdapter.setMyItemClickListener(object : DiaryWriteEmojiRVAdapter.MyItemClickListener{

            override fun onItemClick(emoji: Emoji) {
                binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
                binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE
            }
        })

        binding.diaryWriteEmojiRecyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
        binding.diaryWriteEmojiXIv.setOnClickListener {
            finish()
        }
    }


}