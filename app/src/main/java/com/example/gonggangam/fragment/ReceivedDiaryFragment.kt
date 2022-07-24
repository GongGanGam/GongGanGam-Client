package com.example.gonggangam.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggangam.activity.AcceptDiaryActivity
import com.example.gonggangam.model.Diary
import com.example.gonggangam.adapter.LetterReceivedDiaryRVAdapter
import com.example.gonggangam.diaryService.DiaryRetrofitInterface
import com.example.gonggangam.diaryService.ReceivedDiarysResponse
import com.example.gonggangam.util.PrefManager
import com.example.gonggangam.databinding.FragmentReceivedDiaryBinding
import com.example.gonggangam.util.getRetrofit
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
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReceivedDiaryBinding.inflate(inflater, container, false)
        jwt = PrefManager.jwt
        getData()
        return binding.root
    }

    private fun getData() {
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        diaryService.getDiaries(1).enqueue(object: Callback<ReceivedDiarysResponse> {
            override fun onResponse(
                call: Call<ReceivedDiarysResponse>,
                response: Response<ReceivedDiarysResponse>
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

            override fun onFailure(call: Call<ReceivedDiarysResponse>, t: Throwable) {
                Log.d("TAG/API-ERROR", t.message.toString())
            }

        })
    }

    private fun initRecyclerView() {
        binding.receivedDiaryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val letterReceivedDiaryRVAdapter = LetterReceivedDiaryRVAdapter(requireContext(), diaries)
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
        intent.putExtra("diaryIdx", diary.diaryIdx)
        startActivity(intent)
    }

}