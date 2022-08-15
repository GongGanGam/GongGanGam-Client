package com.example.gonggangam.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gonggangam.model.ReceivedDiary
import com.example.gonggangam.diaryService.DiaryRetrofitInterface
import com.example.gonggangam.diaryService.ReceivedDiaryResponse
import com.example.gonggangam.R
import com.example.gonggangam.util.ImageLoader
import com.example.gonggangam.databinding.ActivityAcceptDiaryBinding
import com.example.gonggangam.util.BindingAdapter
import com.example.gonggangam.util.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class AcceptDiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityAcceptDiaryBinding
    var diary = ReceivedDiary() // receivedDiary API로 호출한 diary
    var diaryIdx: Int = -1// 받아온 diary idx
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcceptDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        diaryIdx = intent.getIntExtra("diaryIdx", -1) // 받은 일기 리스트 화면에서 넘어온 diaryIdx
        Log.d("TAG-DIARY", diaryIdx.toString())
        getData()
    }

    private fun getData() {
        if(diaryIdx < 0) {
            Toast.makeText(this, "데이터 로드 실패", Toast.LENGTH_SHORT).show()
            // 다시 idx 요청
            return
        }
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        diaryService.receivedDiary(diaryIdx).enqueue(object:
            Callback<ReceivedDiaryResponse> {
            override fun onResponse(
                call: Call<ReceivedDiaryResponse>,
                response: Response<ReceivedDiaryResponse>
            ) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> {
                            diary = resp.result!!
                            initListener() // 위치변경
                        }
                        else -> Log.d("TAG/API-CODE", "다이어리 로드 실패" )
                    }
                    Log.d("TAG-API",diary.toString())
                }
            }

            override fun onFailure(call: Call<ReceivedDiaryResponse>, t: Throwable) {
                Log.d("TAG-API/ERROR", t.message.toString())
            }

        })
    }

    private fun initListener() {

        binding.acceptDiaryMenuIv.setOnClickListener {
            if(binding.acceptDiaryReportButtonBtn.visibility == View.GONE){
                binding.acceptDiaryReportButtonBtn.visibility = View.VISIBLE
            }
            else if(binding.acceptDiaryReportButtonBtn.visibility == View.VISIBLE){
                binding.acceptDiaryReportButtonBtn.visibility = View.GONE
            }
        }

        binding.acceptDiaryReportButtonBtn.setOnClickListener{
            goToReportActivity()
        }

        binding.acceptDiaryBackIv.setOnClickListener {
            finish()
        }

        binding.acceptDiaryReplyBtn.setOnClickListener {
            val intent = Intent(this@AcceptDiaryActivity, ReplyToDiaryActivity::class.java)
            intent.putExtra("diary", diary)
            startActivity(intent)
        }

        BindingAdapter.loadProfileImage(
            diary.userProfImg,
            binding.acceptDiaryProfileIv,
            ContextCompat.getDrawable(this, R.drawable.default_profile_img)!!
        )

        binding.acceptDiaryContentTv.text = diary.diaryContent
        binding.acceptDiaryNameTv.text = diary.userNickname
        binding.acceptDiaryDateTv.text = diary.diaryDate
    }

    private fun goToReportActivity() {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra("reportType", "diary")
        intent.putExtra("idxOfType", diary.diaryIdx)
        intent.putExtra("reportUserName", diary.userNickname)
        startActivity(intent)
    }
}