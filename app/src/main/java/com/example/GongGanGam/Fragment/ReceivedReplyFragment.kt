package com.example.gonggangam.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggangam.Activity.AcceptChattingActivity
import com.example.gonggangam.Adapter.LetterReceivedAnswerRVAdapter
import com.example.gonggangam.Class.Diary
import com.example.gonggangam.Adapter.LetterReceivedDiaryRVAdapter
import com.example.gonggangam.Class.Answer
import com.example.gonggangam.DiaryService.DiaryRetrofitInterface
import com.example.gonggangam.DiaryService.ReceivedAnswerResponse
import com.example.gonggangam.DiaryService.ReceivedDiaryResponse
import com.example.gonggangam.databinding.FragmentReceivedReplyBinding
import com.example.gonggangam.getJwt
import com.example.gonggangam.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceivedReplyFragment : Fragment() {
    lateinit var binding: FragmentReceivedReplyBinding
    lateinit var jwt: String
    private var answers = ArrayList<Answer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReceivedReplyBinding.inflate(inflater, container, false)
        jwt = getJwt(requireContext())
        getData()
        initRecyclerView()

        return binding.root
    }

    private fun getData() {
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        diaryService.getAnswers(1, jwt).enqueue(object: Callback<ReceivedAnswerResponse> {
            override fun onResponse(
                call: Call<ReceivedAnswerResponse>,
                response: Response<ReceivedAnswerResponse>
            ) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> answers = resp.result!!.answers
                        else -> Log.d("TAG/API-CODE", "다이어리 읽기 실패" )
                    }
                    Log.d("TAG-API", answers.toString())
                    initRecyclerView()
                }
            }

            override fun onFailure(call: Call<ReceivedAnswerResponse>, t: Throwable) {
                Log.d("TAG/API-ERROR", t.message.toString())
            }


        })
    }

    private fun initRecyclerView() {
        binding.receivedReplyRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val letterReceivedAnswerRVAdapter = LetterReceivedAnswerRVAdapter(answers)
        binding.receivedReplyRv.adapter = letterReceivedAnswerRVAdapter

        letterReceivedAnswerRVAdapter.setOnItemClickListener(object:
            LetterReceivedAnswerRVAdapter.OnItemClickListener {
            override fun onItemClick(answer: Answer) {
                goToAcceptChatting() // 받은 일기 답장 액티비티로 전환
            }

        })
    }

    private fun goToAcceptChatting() {
        val intent = Intent(activity, AcceptChattingActivity::class.java)
        startActivity(intent)
    }

}