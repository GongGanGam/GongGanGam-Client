package com.example.gonggangam.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gonggangam.Activity.ChatActivity
import com.example.gonggangam.Class.ChatList
import com.example.gonggangam.Adapter.TChatListRVAdapter
import com.example.gonggangam.Class.ChatModel
import com.example.gonggangam.Class.Comment
import com.example.gonggangam.Class.User
import com.example.gonggangam.databinding.FragmentChatBinding
import com.example.gonggangam.databinding.ItemChatListBinding
import com.example.gonggangam.getUserIdx
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding
    lateinit var mDatabase: DatabaseReference
//    private val userIdx : String? = getUserIdx(requireContext()).toString() // 내 idx

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
        mDatabase = FirebaseDatabase.getInstance().reference
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.chatRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        val chatListRVAdapter = TChatListRVAdapter(chatLists)
//        binding.chatRv.adapter = chatListRVAdapter
//
//        chatListRVAdapter.setOnItemClickListener(object: ChatListRVAdapter.OnItemClickListener {
//
//            override fun onItemClick(chatList: ChatList) {
//                goToChat()
//            }
//        })

        val chatListRVAdapter = ChatListRVAdapter()
        binding.chatRv.adapter = chatListRVAdapter
    }

    private fun goToChat() {
        val intent = Intent(activity, ChatActivity::class.java)
        // val intent = Intent(activity, ChatActivitySample::class.java)
        startActivity(intent)
    }

    fun convertTimestampToDate(timestamp: Long): String? {
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(timestamp) as String
    }


    inner class ChatListRVAdapter: RecyclerView.Adapter<ChatListRVAdapter.ViewHolder>() {
        private val chatModel = ArrayList<ChatModel>()
        private val oppUsers : ArrayList<User> = arrayListOf() // 채팅 상대 정
        private val uid : Int = 8

        init {
            mDatabase.child("chatRooms").orderByChild("users/${uid}_key").equalTo(true).addListenerForSingleValueEvent(object :
                ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatModel.clear()
                    for(data in snapshot.children) {
                        chatModel.add(data.getValue<ChatModel>()!!)
                        println(data)
                    }
                    notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }

            })

        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val binding: ItemChatListBinding = ItemChatListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var oppId: String? = null
            for (user in chatModel[position].users.keys) {
                if(user != uid.toString()+"_key") {
                    oppId = user
                    oppUsers.add(chatModel[position].opp[oppId]!!)
                }
            }
//            mDatabase.child("opp").child("$oppId").addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
////                    val user = snapshot.getValue<User>()
//                    val user = snapshot.value as User
//                    Log.d("TAG_CHATFRAG", oppId.toString())
//                    Log.d("TAG_CHATFRAG", snapshot.toString())
//                    Log.d("TAG_CHATFRAG", user.toString())
//                    // profile img
//                    Glide.with(holder.itemView.context).load(user!!.profImg)
//                        .apply(RequestOptions().circleCrop())
//                        .into(holder.binding.charListProfileIv)
//
//                    // nickname
//                    holder.binding.chatListOppNameTv.text = user.nickname
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                }
//
//            })

            Glide.with(holder.itemView.context).load(chatModel[position].opp[oppId]!!.profImg)
                .apply(RequestOptions().circleCrop())
                .into(holder.binding.charListProfileIv)

            // nickname
            holder.binding.chatListOppNameTv.text = chatModel[position].opp[oppId]!!.nickname

            // last message & time
            val commentMap = TreeMap<String, Comment>(reverseOrder())
            commentMap.putAll(chatModel[position].comments)
            val lastMessageKey = commentMap.keys.toTypedArray()[0]
            holder.binding.chatListContentTv.text = chatModel[position].comments[lastMessageKey]?.message
            holder.binding.chatListDate.text = convertTimestampToDate(chatModel[position].comments[lastMessageKey]?.timeStamp!!)
        }

        override fun getItemCount(): Int {
            return chatModel.size
        }

        inner class ViewHolder(val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(chatList: ChatList) {
                binding.chatListOppNameTv.text = chatList.oppName
                binding.chatListDate.text = chatList.date
                binding.chatListContentTv.text = chatList.content
            }
        }
    }

}