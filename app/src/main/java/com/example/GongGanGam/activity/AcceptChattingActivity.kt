package com.example.gonggangam.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.GongGanGam.activity.ChatActivity
import com.example.GongGanGam.activity.ReportActivity
import com.example.gonggangam.Class.BasicDiary
import com.example.gonggangam.Class.ReceivedAnswer
import com.example.gonggangam.Class.User
import com.example.GongGanGam.diaryService.BasicResponse
import com.example.GongGanGam.diaryService.DiaryRetrofitInterface
import com.example.GongGanGam.diaryService.ReceivedAnswerResponse
import com.example.gonggangam.R
import com.example.gonggangam.Util.ImageLoader
import com.example.gonggangam.databinding.ActivityAcceptChattingBinding
import com.example.gonggangam.getJwt
import com.example.gonggangam.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AcceptChattingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAcceptChattingBinding
    var diary = BasicDiary()
    var answer = ReceivedAnswer()
    var answerIdx: Int = -1 // 답장 리스트 프래그먼트에서 넘어온 idx
    val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcceptChattingBinding.inflate(layoutInflater)
        initListener()
        answerIdx = intent.getIntExtra("answerIdx", -1)
        //API 통신 시작
        getData()
        setContentView(binding.root)
    }

    private fun getData() {
        if(answerIdx == null) {
            Toast.makeText(this, "답장 정보 조회 실패", Toast.LENGTH_SHORT).show()
            return
        }
        diaryService.receivedAnswer(getJwt(this), answerIdx).enqueue(object: Callback<ReceivedAnswerResponse> {
            override fun onResponse(
                call: Call<ReceivedAnswerResponse>,
                response: Response<ReceivedAnswerResponse>
            ) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> {
                            diary = resp.result!!.diary
                            answer = resp.result.answer
                            initListener()
                        }
                        else -> Log.d("TAG/API-CODE", "서버는 들어갔으나 데이터 no" )
                    }
                }
            }

            override fun onFailure(call: Call<ReceivedAnswerResponse>, t: Throwable) {
                Log.d("TAG/API-ERROR", t.message.toString());
            }

        })
    }

    private fun rejectAnswer() {
        diaryService.rejectAnswer(getJwt(this), answerIdx).enqueue(object: Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> {
                           binding.acceptChattingFooter.visibility = View.GONE
                        }
                        else -> Log.d("TAG/API-CODE", "서버는 들어갔으나 데이터 no" )
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("TAG/API-ERROR", t.message.toString())
            }

        })
    }
    @SuppressLint("SetTextI18n")
    private fun initListener() {
        // 내 일기
        binding.acceptChattingMyDate.text = diary.diaryDate
        if(diary.diaryContent == null) {
            binding.acceptChattingMyContent.text = ""
        }
        else {
            if(diary.diaryContent!!.length > 26) {
                binding.acceptChattingMyContent.text = diary.diaryContent!!.slice(IntRange(0, 23))+"..."
            }
            else {
                binding.acceptChattingMyContent.text = diary.diaryContent
            }
        }
        binding.acceptChattingMyContent.text = diary.diaryContent
        binding.acceptChattingEmojiIv.setImageResource(resources.getIdentifier("emoji_"+diary.emoji, "drawable", packageName))

        // 받은 답장
        binding.acceptChattingNameTv.text = answer.nickname
        binding.acceptChattingDateTv.text = answer.answerDate
        binding.acceptChattingContentTv.text = answer.answerContents
        if(answer.profImg == null) {
            binding.acceptChattingProfileIv.setImageResource(R.drawable.default_profile_img)
        }
        else {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(answer.profImg!!)
                }

                // Glide 적용
                Glide.with(this@AcceptChattingActivity).load(bitmap).circleCrop().into(binding.acceptChattingProfileIv)
//                binding.acceptChattingProfileIv.setImageBitmap(bitmap)
            }
        }

        // 거절 버튼
        if(answer.isReject == 'F') { //  isReject, 거절안한 상태
            binding.acceptChattingFooter.visibility = View.VISIBLE
        }
        else { // 거절 상태
            binding.acceptChattingFooter.visibility = View.GONE
        }
        binding.acceptChattingRejectBtn.setOnClickListener {
            // 거절 api
            rejectAnswer()
        }

        binding.acceptChattingStartBtn.setOnClickListener {
            val intent = Intent(this@AcceptChattingActivity, ChatActivity::class.java)
            intent.putExtra("opp", User(answer.nickname!!, answer.profImg!!, answer.answerIdx))
            startActivity(intent)
        }

        binding.acceptChattingBackIv.setOnClickListener {
            finish()
        }

        binding.acceptChattingMenuIv.setOnClickListener {
            goToReportActivity()
        }

    }

    private fun goToReportActivity() {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra("reportType", "answer")
        intent.putExtra("idxOfType", answer.answerIdx)
        intent.putExtra("reportUserName", answer.nickname)
        startActivity(intent)
    }
}