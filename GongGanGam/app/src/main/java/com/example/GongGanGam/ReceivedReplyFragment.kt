package com.example.GongGanGam

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GongGanGam.databinding.FragmentReceivedReplyBinding

class ReceivedReplyFragment : Fragment() {
    lateinit var binding: FragmentReceivedReplyBinding

    private val replies = arrayListOf<Diary>(
        Diary("", "오늘도맑음", "21.12.24", "기억하니 우리가 했던 이별 시간이 멈춘 듯..."),
        Diary("", "오늘도맑음", "21.12.24", "기억하니 우리가 했던 이별 시간이 멈춘 듯..."),
        Diary("", "오늘도맑음", "21.12.24", "기억하니 우리가 했던 이별 시간이 멈춘 듯..."),
        Diary("", "오늘도맑음", "21.12.24", "기억하니 우리가 했던 이별 시간이 멈춘 듯..."),
        Diary("", "오늘도맑음", "21.12.24", "기억하니 우리가 했던 이별 시간이 멈춘 듯..."),
        Diary("", "오늘도맑음", "21.12.24", "기억하니 우리가 했던 이별 시간이 멈춘 듯..."),
        Diary("", "오늘도맑음", "21.12.24", "기억하니 우리가 했던 이별 시간이 멈춘 듯...")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReceivedReplyBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.receivedReplyRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val letterReceivedDiaryRVAdapter = LetterReceivedDiaryRVAdapter(replies)
        binding.receivedReplyRv.adapter = letterReceivedDiaryRVAdapter

        letterReceivedDiaryRVAdapter.setOnItemClickListener(object: LetterReceivedDiaryRVAdapter.OnItemClickListener{
            override fun onItemClick(diary: Diary) {
                goToAcceptChatting() // 받은 일기 답장 액티비티로 전환
            }
        })
    }

    private fun goToAcceptChatting() {
        val intent = Intent(activity, AcceptChattingActivity::class.java)
        startActivity(intent)
    }

}