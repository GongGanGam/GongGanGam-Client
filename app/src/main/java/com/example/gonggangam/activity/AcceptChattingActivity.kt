package com.example.gonggangam.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.gonggangam.model.BasicDiary
import com.example.gonggangam.model.ReceivedAnswer
import com.example.gonggangam.model.User
import com.example.gonggangam.diaryService.BasicResponse
import com.example.gonggangam.diaryService.DiaryRetrofitInterface
import com.example.gonggangam.diaryService.ReceivedAnswerResponse
import com.example.gonggangam.R
import com.example.gonggangam.util.ImageLoader
import com.example.gonggangam.databinding.ActivityAcceptChattingBinding
import com.example.gonggangam.util.BindingAdapter
import com.example.gonggangam.util.getRetrofit
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
    val diaryService: DiaryRetrofitInterface = getRetrofit().create(DiaryRetrofitInterface::class.java)
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
        diaryService.receivedAnswer(answerIdx).enqueue(object: Callback<ReceivedAnswerResponse> {
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
        diaryService.rejectAnswer(answerIdx).enqueue(object: Callback<BasicResponse> {
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

        BindingAdapter.loadProfileImage(
            answer.profImg,
            binding.acceptChattingProfileIv,
            ContextCompat.getDrawable(this, R.drawable.default_profile_img)!!
        )

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
            diaryService.startChat(answer.answerUserIdx!!).enqueue(object: Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful && response.code() == 200) {
                        val resp = response.body()!!
                        Log.d("TAG/API-RESPONSE", resp.toString())

                        when(resp.code) {
                            1000 -> {
                                Log.d("TAG/API-CODE", "채팅 시작 성공")

                                // 채팅 페이지 접속
                                val intent = Intent(this@AcceptChattingActivity, ChatActivity::class.java)
                                intent.putExtra("opp", User(answer.answerIdx, answer.profImg!!, answer.nickname!!))
                                startActivity(intent)
                            }
                            else -> Log.d("TAG/API-CODE", "채팅 시작 실패" )
                        }
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("TAG-FAIL", "채팅 시작 실패")
                }

            })
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