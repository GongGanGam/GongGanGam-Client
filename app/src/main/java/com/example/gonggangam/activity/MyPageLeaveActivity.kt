package com.example.gonggangam.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.databinding.ActivityMyPageLeaveBinding
import com.example.gonggangam.diaryService.BasicResponse
import com.example.gonggangam.model.NoticeModel
import com.example.gonggangam.myPageService.MyPageRetrofitInterface
import com.example.gonggangam.myPageService.NoticeResponse
import com.example.gonggangam.util.AuthUtil
import com.example.gonggangam.util.PrefManager
import com.example.gonggangam.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageLeaveActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPageLeaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageLeaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mypageLeaveXIv.setOnClickListener {
            finish()
        }

        binding.mypageLeaveCancelBtn.setOnClickListener {
            finish()
        }

        binding.mypageLeaveWithdrawalBtn.setOnClickListener {
            setWithdrawal()
        }

    }

    private fun setWithdrawal() {
        val myPageService = getRetrofit().create(MyPageRetrofitInterface::class.java)
        myPageService.setWithdrawal(PrefManager.userIdx).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    AuthUtil.logout(this@MyPageLeaveActivity)

                    Log.d(MyPageNoticeActivity.TAG, "retrofit success : $body")
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d(MyPageNoticeActivity.TAG, "retrofit fail : ${t.message}")
            }
        })
    }
}
