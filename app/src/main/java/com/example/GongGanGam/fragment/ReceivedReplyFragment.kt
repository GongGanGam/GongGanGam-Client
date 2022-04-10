package com.example.GongGanGam.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GongGanGam.activity.AcceptChattingActivity
import com.example.GongGanGam.adapter.LetterReceivedAnswerRVAdapter
import com.example.GongGanGam.model.Answer
import com.example.GongGanGam.diaryService.DiaryRetrofitInterface
import com.example.GongGanGam.diaryService.ReceivedAnswersResponse
import com.example.gonggangam.databinding.FragmentReceivedReplyBinding
import com.example.GongGanGam.util.getJwt
import com.example.GongGanGam.util.getRetrofit
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
        diaryService.getAnswers(1, jwt).enqueue(object: Callback<ReceivedAnswersResponse> {
            override fun onResponse(
                call: Call<ReceivedAnswersResponse>,
                response: Response<ReceivedAnswersResponse>
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

            override fun onFailure(call: Call<ReceivedAnswersResponse>, t: Throwable) {
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
                goToAcceptChatting(answer.answerIdx!!) // 받은 일기 답장 액티비티로 전환
            }

        })
    }

    private fun goToAcceptChatting(answerIdx: Int) {
        val intent = Intent(activity, AcceptChattingActivity::class.java)
        intent.putExtra("answerIdx", answerIdx)
        startActivity(intent)
    }

}