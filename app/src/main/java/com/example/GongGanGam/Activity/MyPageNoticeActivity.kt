package com.example.gonggangam.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggangam.Adapter.MypageNoticeRVAdapter
import com.example.gonggangam.Class.Notice
import com.example.gonggangam.Class.NoticeModel
import com.example.gonggangam.MyPageService.MyPageRetrofitInterface
import com.example.gonggangam.databinding.ActivityMyPageNoticeBinding
import kotlinx.android.synthetic.main.activity_my_page_notice.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageNoticeActivity:AppCompatActivity() {
    lateinit var binding: ActivityMyPageNoticeBinding
//    private lateinit var noticeDatas: ArrayList<Notice>;
//    private lateinit var adapter: MypageNoticeRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val recyclerView = findViewById<RecyclerView>(R.id.mypage_notice_recyclerView)

//        noticeDatas = ArrayList()
//        noticeDatas.apply{
//            add(Notice("설 연휴 고객센터 운영 안내","22.01.22", "안녕하세요. 공간감입니다.\n\n설 연휴 기간 고객센터 운영 시간 안내해 드립니다.\n\n휴무일: 2022년 1월 29일(토)-2022년 2월 2일(수)\n\n휴무 기간 남겨주신 문의는 순차적으로 답변드릴 예정입니다."))
//            add(Notice("공간감 버전 업데이트 안내","22.01.11","안녕하세요. 공간감에서 안내 드립니다.\n\n공간감이 새롭게 개편되어 찾아왔습니다!\n지난 버전에 분분했던 의견을 적극 반영하고 더욱 좋은 모습으로 찾아뵙기 위해 많은 고민에 고민을 더했습니다.\n\n 새로워진 공간감에 더 좋은 기억을 남겨주세요:)"))
//            add(Notice("안녕하세요. 공간감입니다.","22.01.03","안녕하세요. 공간감입니다.\n\n자신의 감정의 흐름을 읽고 다른 사람의 이야기를 들으며 조금 더 단단한 사람들이 많아졌으면 하는 바람으로 앱을 기획하게 되었습니다.\n\n바쁘디 바쁜 현대사회를 살고 있는 사람들에게 마음 챙김을 줄 수 있는 서비스,\n공간감을 여러분과 함께 시작해보려 합니다."))
//        }

//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = MypageNoticeRVAdapter(noticeDatas)
//        recyclerView.adapter = adapter

        binding.mypageNoticeBackIv.setOnClickListener {
            finish()
        }
        loadData()
    }

    private fun setAdapter(noticeList : ArrayList<Notice>){
        val mAdapter = MypageNoticeRVAdapter(noticeList)
        mypage_notice_recyclerView.adapter = mAdapter
        mypage_notice_recyclerView.layoutManager = LinearLayoutManager(this)
        mypage_notice_recyclerView.setHasFixedSize(false)
    }



    private fun loadData() {
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
    }




}