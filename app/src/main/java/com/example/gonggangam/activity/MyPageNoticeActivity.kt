package com.example.gonggangam.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggangam.adapter.MypageNoticeRVAdapter
import com.example.gonggangam.model.NoticeModel
import com.example.gonggangam.myPageService.MyPageRetrofitInterface
import com.example.gonggangam.myPageService.NoticeResponse
import com.example.gonggangam.util.getRetrofit
import com.example.gonggangam.databinding.ActivityMyPageNoticeBinding
import kotlinx.android.synthetic.main.activity_my_page_notice.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageNoticeActivity:AppCompatActivity() {
    lateinit var binding: ActivityMyPageNoticeBinding

    companion object {
        const val TAG = "MyPageNotice"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mypageNoticeBackIv.setOnClickListener {
            finish()
        }
        loadData()
    }

    private fun setAdapter(noticeList : ArrayList<NoticeModel>){
        val mAdapter = MypageNoticeRVAdapter(noticeList)
        mypage_notice_recyclerView.adapter = mAdapter
        mypage_notice_recyclerView.layoutManager = LinearLayoutManager(this)
        mypage_notice_recyclerView.setHasFixedSize(false)
    }



    private fun loadData() {

        val myPageService = getRetrofit().create(MyPageRetrofitInterface::class.java)
        myPageService.getNoticeList().enqueue(object : Callback<NoticeResponse> {
            override fun onResponse(call: Call<NoticeResponse>, response: Response<NoticeResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    body?.result?.let { it ->
                        setAdapter(it.notices.map {
                            NoticeModel(
                                title = it.title,
                                noticeContent = it.noticeContent,
                                noticeDate = it.noticeDate,
                                isExpanded = false
                            )
                        } as ArrayList<NoticeModel>)

                        if (it.notices.isEmpty())
                            binding.tvEmptyList.visibility = View.VISIBLE
                        else
                            binding.tvEmptyList.visibility = View.INVISIBLE

                        Log.d(TAG, "retrofit success : $body")
                    }
                }
            }

            override fun onFailure(call: Call<NoticeResponse>, t: Throwable) {
                Log.d(TAG, "retrofit fail : ${t.message}")
            }
        })
    }




}