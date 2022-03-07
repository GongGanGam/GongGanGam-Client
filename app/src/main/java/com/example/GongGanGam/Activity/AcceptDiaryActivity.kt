package com.example.gonggangam.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.Class.Diary
import com.example.gonggangam.R
import com.example.gonggangam.Util.ImageLoader
import com.example.gonggangam.databinding.ActivityAcceptDiaryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class AcceptDiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAcceptDiaryBinding
    lateinit var diary: Diary // 받아온 다이어리 데이터
    val dateFormat = SimpleDateFormat("yyyy.MM.dd")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcceptDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        diary = intent.getSerializableExtra("diary") as Diary // 받은 일기 리스트 화면에서 넘어온 다이어리 정보
        Log.d("TAG-DIARY", diary.toString())
        initListener()
    }

    private fun initListener() {
        binding.acceptDiaryBackIv.setOnClickListener {
            finish()
        }

        binding.acceptDiaryReplyBtn.setOnClickListener {
            val intent = Intent(this@AcceptDiaryActivity, ReplyToDiaryActivity::class.java)
            intent.putExtra("diary", diary)
            startActivity(intent)
        }
        if(diary.userProfImg == null) {
            binding.acceptDiaryProfileIv.setImageResource(R.drawable.default_profile_img)
        }
        else {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(diary.userProfImg!!)
                }
                binding.acceptDiaryProfileIv.setImageBitmap(bitmap)
            }
        }
        var date = dateFormat.parse(diary.diaryDate)
        binding.acceptDiaryContentTv.text = diary.diaryContents
        binding.acceptDiaryNameTv.text = diary.userNickname
        binding.acceptDiaryDateTv.text = diary.diaryDate
    }
}