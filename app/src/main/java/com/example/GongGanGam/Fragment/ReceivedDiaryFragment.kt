package com.example.gonggangam.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggangam.Activity.AcceptDiaryActivity
import com.example.gonggangam.Class.Diary
import com.example.gonggangam.Adapter.LetterReceivedDiaryRVAdapter
import com.example.gonggangam.DiaryService.DiaryRetrofitInterface
import com.example.gonggangam.DiaryService.ReceivedDiaryResponse
import com.example.gonggangam.databinding.FragmentReceivedDiaryBinding
import com.example.gonggangam.getJwt
import com.example.gonggangam.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceivedDiaryFragment : Fragment() {
   lateinit var binding : FragmentReceivedDiaryBinding
   lateinit var jwt: String
   private var diaries = ArrayList<Diary>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReceivedDiaryBinding.inflate(inflater, container, false)
        jwt = getJwt(requireContext())
        getData()
        return binding.root
    }

    private fun getData() {
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        diaryService.getDiaries(1, jwt).enqueue(object: Callback<ReceivedDiaryResponse> {
            override fun onResponse(
                call: Call<ReceivedDiaryResponse>,
                response: Response<ReceivedDiaryResponse>
            ) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> diaries = resp.result!!.diarys
                        else -> Log.d("TAG/API-CODE", "다이어리 읽기 실패" )
                    }
                    Log.d("TAG-API", diaries.toString())
                    initRecyclerView()
                }
            }

            override fun onFailure(call: Call<ReceivedDiaryResponse>, t: Throwable) {
                Log.d("TAG/API-ERROR", t.message.toString())
            }

        })
    }

    private fun initRecyclerView() {
        binding.receivedDiaryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val letterReceivedDiaryRVAdapter = LetterReceivedDiaryRVAdapter(diaries)
        binding.receivedDiaryRv.adapter = letterReceivedDiaryRVAdapter

        letterReceivedDiaryRVAdapter.setOnItemClickListener(object:
            LetterReceivedDiaryRVAdapter.OnItemClickListener {
            override fun onItemClick(diary: Diary) {
                goToAcceptDiary(diary) // 받은 일기 답장 액티비티로 전환
            }
        })

    }

    private fun goToAcceptDiary(diary: Diary) {
        val intent = Intent(activity, AcceptDiaryActivity::class.java)
        intent.putExtra("diary", diary)
        startActivity(intent)
    }

}