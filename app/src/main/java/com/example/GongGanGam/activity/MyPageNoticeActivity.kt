package com.example.GongGanGam.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GongGanGam.adapter.MypageNoticeRVAdapter
import com.example.GongGanGam.model.NoticeData
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
                            NoticeModel(it, false)
                        } as ArrayList<NoticeModel>)
                        Log.d(TAG, "retrofit success : $body")
                    }
                }
            }

            override fun onFailure(call: Call<NoticeListData>, t: Throwable) {
                Log.d(TAG, "retrofit fail : ${t.message}")
            }
        })

        /*
        // 이부분은 getRetrofit()으로 대체할 수 있습니다!
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.36.219.12:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // MyPageService를 활용하시면 될것같아요! diary쪽에 제가 작업해둔 예제를 보시고 확인해보세용!
        val retrofitService = retrofit.create(MyPageRetrofitInterface::class.java)
        retrofitService.requestAllData().enqueue(object : Callback<NoticeModel>{
            override fun onResponse(call: Call<NoticeModel>, response: Response<NoticeModel>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        setAdapter(it.notices)
                    }
                }
            }

            override fun onFailure(call: Call<NoticeModel>, t: Throwable) {
                Log.d("this is error",t.message.toString())
            }
        })

         */

    }




}