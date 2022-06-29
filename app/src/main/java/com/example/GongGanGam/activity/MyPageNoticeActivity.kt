package com.example.GongGanGam.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GongGanGam.adapter.MypageNoticeRVAdapter
import com.example.GongGanGam.model.NoticeListData
import com.example.GongGanGam.model.NoticeModel
import com.example.GongGanGam.myPageService.MyPageRetrofitInterface
import com.example.GongGanGam.util.getRetrofit
import com.example.gonggangam.databinding.ActivityMyPageNoticeBinding
import kotlinx.android.synthetic.main.activity_my_page_notice.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageNoticeActivity:AppCompatActivity() {
    lateinit var binding: ActivityMyPageNoticeBinding
//    private lateinit var noticeDatas: ArrayList<Notice>;
//    private lateinit var adapter: MypageNoticeRVAdapter

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
        myPageService.getNoticeList().enqueue(object : Callback<NoticeListData> {
            override fun onResponse(call: Call<NoticeListData>, response: Response<NoticeListData>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { it ->
                        setAdapter(it.notices.map {
                            NoticeModel(
                                title = it.title,
                                noticeContent = it.noticeContent,
                                noticeDate = it.noticeDate,
                                isExpanded = false
                            )
                        } as ArrayList<NoticeModel>)
                        Log.d(TAG, "retrofit success : $body")
                    }
                }
            }

            override fun onFailure(call: Call<NoticeListData>, t: Throwable) {
                Log.d(TAG, "retrofit fail : ${t.message}")
            }
        })
    }




}