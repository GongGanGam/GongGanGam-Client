package com.example.gonggangam.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggangam.Activity.ChatActivity
import com.example.gonggangam.Class.ChatList
import com.example.gonggangam.Adapter.ChatListRVAdapter
import com.example.gonggangam.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding

    private val chatLists = arrayListOf<ChatList>(
        ChatList("", "안녕안녕", "21.12.24", "그럼 오늘은 뭐 볼거야?"),
        ChatList("", "영화빌런", "21.01.14", "이번에 개봉하는 영화 어때?"),
        ChatList("", "이불밖은위험해", "21.02.11", "안녕하세요! 일기가 정말 인상 깊어서 꼭 이야..."),
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.chatRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val chatListRVAdapter = ChatListRVAdapter(chatLists)
        binding.chatRv.adapter = chatListRVAdapter

        chatListRVAdapter.setOnItemClickListener(object: ChatListRVAdapter.OnItemClickListener {

            override fun onItemClick(chatList: ChatList) {
                goToChat()
            }
        })
    }

    private fun goToChat() {
        val intent = Intent(activity, ChatActivity::class.java)
        // val intent = Intent(activity, ChatActivitySample::class.java)
        startActivity(intent)
    }

}