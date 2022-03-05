package com.example.gonggangam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggangam.databinding.FragmentReceivedReplyBinding

class ReceivedReplyFragment : Fragment() {
    lateinit var binding: FragmentReceivedReplyBinding

    private val replies = arrayListOf<Diary>(
        Diary("", "오늘도맑음", "21.12.24", "오늘은 날씨가 참 맑았다. 날씨와는 반대로..."),
        Diary("", "영화빌런","21.12.24", "스파이더맨 진짜 재밌었다. 특히 역대 스파이..."),
        Diary("", "캐롤과휘리릭", "21.12.24", "오늘은 날씨가 참 맑았다. 날씨와는 반대로..."),
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