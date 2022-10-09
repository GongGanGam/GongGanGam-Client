package com.example.gonggangam.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gonggangam.diaryService.DiaryRetrofitInterface
import com.example.gonggangam.diaryService.ReadDiaryResponse
import com.example.gonggangam.util.BindingAdapter
import com.example.gonggangam.util.getRetrofit
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityDiaryReadBinding
import com.example.gonggangam.diaryService.ReadDiary
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.Serializable

class DiaryReadActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryReadBinding

    private var year = -1
    private var month = -1
    private var day = -1
    private var diary: ReadDiary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryReadBinding.inflate(layoutInflater)
        getData()
        initListener()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        year = intent.getIntExtra("year", -1)
        month = intent.getIntExtra("month", -1)
        day = intent.getIntExtra("day", -1)

        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        diaryService.getDiary(year, month, day).enqueue(object: Callback<ReadDiaryResponse> {
            override fun onResponse(
                call: Call<ReadDiaryResponse>,
                response: Response<ReadDiaryResponse>
            ) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> {
                            val emoji: String  = resp.result.emoji
                            val date: String  = resp.result.diaryDate
                            val contents: String  = resp.result.contents
                            val img: String?  = resp.result.image
                            val answer = resp.result.answer
                            diary = resp.result

                            // Diary Binding
                            BindingAdapter.loadEmoji(emoji, binding.diaryReadMoodIv)
                            binding.diaryReadDateTv.text = date
                            binding.diaryReadContentTv.text = contents
                            if(img != null) {
                                BindingAdapter.loadDiaryImage(img, binding.diaryReadContentIv)
                                binding.diaryReadContentIv.visibility = View.VISIBLE
                            }

                            // Answer Binding
                            if(answer != null) {
                                binding.diaryReadMyCl.visibility = View.VISIBLE
                                BindingAdapter.loadProfileImage(answer.userProfImg,
                                    binding.diaryReadSenderProfileIv,
                                    ContextCompat.getDrawable(this@DiaryReadActivity, R.drawable.default_profile_img)!!
                                )
                                binding.diaryReadSendDate.text = answer.answerTime
                                binding.diaryReadSender.text = answer.nickname
                                binding.diaryReadMyContent.text = answer.answerContent
                            }
                        }
                        else -> Log.d("TAG/API-CODE", "다이어리 로드 실패" )
                    }
                }
            }

            override fun onFailure(call: Call<ReadDiaryResponse>, t: Throwable) {
                Log.d("TAG-API/ERROR", t.message.toString())
            }
        })
    }

    private fun initListener() {
        binding.diaryReadHeader.layoutHeaderBackIv.setOnClickListener {
            finish()
        }

        //헤더 내의 내용 수정
        binding.diaryReadHeader.layoutHeaderBtnTv.text= "수정"
        binding.diaryReadHeader.layoutHeaderMenuIv.visibility= View.INVISIBLE
        binding.diaryReadHeader.layoutHeaderTitleTv.visibility= View.INVISIBLE

        binding.diaryReadHeader.layoutHeaderBtnTv.setOnClickListener {

            if (diary != null) {
                val intent = Intent(this, DiaryWriteActivity::class.java)
                startActivity(intent.apply {
                    putExtra("year", year)
                    putExtra("month", month)
                    putExtra("day", day)
                    putExtra("diary", diary)
                    putExtra("state", diary!!.emoji)
                    putExtra("image", getEmojiImage(diary!!.emoji))
                })
            }
        }

        binding.diaryReadHeader.layoutHeaderBackIv.setOnClickListener {
            finish()
        }

        binding.diaryReadMyCl.setOnClickListener {
            val intent = Intent(this, AcceptDiaryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getEmojiImage(emojiStr: String): ByteArray {
        val stream = ByteArrayOutputStream()
        val dId: Int = when (emojiStr) {
            "depressed" -> R.drawable.emoji_depressed
            "annoyed" -> R.drawable.emoji_annoyed
            "boring" -> R.drawable.emoji_boring
            "complicated" -> R.drawable.emoji_complicated
            "embarrassing" -> R.drawable.emoji_embarrassing
            "excited" -> R.drawable.emoji_excited
            "fun" -> R.drawable.emoji_fun
            "happy" -> R.drawable.emoji_happy
            "sad" -> R.drawable.emoji_sad
            "soso" -> R.drawable.emoji_soso
            "upset" -> R.drawable.emoji_upset
            "wonder" -> R.drawable.emoji_wonder
            else -> R.drawable.emoji_happy
        }

        val bitmap =
            (getDrawable(dId) as BitmapDrawable).bitmap
        val scale = (1024 / bitmap.width.toFloat())
        val image_w = (bitmap.width * scale).toInt()
        val image_h = (bitmap.height * scale).toInt()
        val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        return stream.toByteArray()
    }
}