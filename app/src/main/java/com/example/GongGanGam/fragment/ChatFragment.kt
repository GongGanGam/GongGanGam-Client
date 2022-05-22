package com.example.GongGanGam.fragment

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
import com.example.GongGanGam.activity.ChatActivity
import com.example.GongGanGam.diaryService.ChatListResponse
import com.example.GongGanGam.diaryService.DiaryRetrofitInterface
import com.example.GongGanGam.model.ChatList
import com.example.GongGanGam.model.ChatModel
import com.example.GongGanGam.model.Comment
import com.example.GongGanGam.model.Diary
import com.example.gonggangam.databinding.FragmentChatBinding
import com.example.gonggangam.databinding.ItemChatListBinding
import com.example.GongGanGam.util.getJwt
import com.example.GongGanGam.util.getRetrofit
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding
    lateinit var mDatabase: DatabaseReference
    private var chatLists = ArrayList<ChatList>()
//    private val userIdx : String? = getUserIdx(requireContext()).toString() // 내 idx

//    private val chatLists = arrayListOf<ChatList>(
//        ChatList("", "안녕안녕", "21.12.24", "그럼 오늘은 뭐 볼거야?"),
//        ChatList("", "영화빌런", "21.01.14", "이번에 개봉하는 영화 어때?"),
//        ChatList("", "이불밖은위험해", "21.02.11", "안녕하세요! 일기가 정말 인상 깊어서 꼭 이야..."),
//    )

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

    private fun getChatList() {
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        diaryService.getChatList(getJwt(requireContext())).enqueue(object: Callback<ChatListResponse> {
            override fun onResponse(
                call: Call<ChatListResponse>,
                response: Response<ChatListResponse>
            ) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG-CHATLIST", resp.toString())

                    when(resp.code) {
                        1000 -> chatLists = resp.result
                        else -> Log.d("TAG-CHATLIST", "채팅 리스트 불러오기 실패" )
                    }
                }
                Log.d("TAG-CHATLIST", chatLists.toString())
            }

            override fun onFailure(call: Call<ChatListResponse>, t: Throwable) {
                Log.d("TAG-CHATLIST", t.message.toString())
            }
        })
    }


    fun convertTimestampToDate(timestamp: Long): String? {
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(timestamp) as String
    }


    inner class ChatListRVAdapter: RecyclerView.Adapter<ChatListRVAdapter.ViewHolder>() {
        private val chatModel = ArrayList<ChatModel>()
        private val oppUsers : ArrayList<String> = arrayListOf() // 채팅 상대 userIdx
        private val uid : Int = 8

        init {
            getChatList()
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
            for (user in chatModel[position].users.keys) { // 채팅방의 user들 중 내 key가 아닌 것
                if(user != uid.toString()+"_key") {
                    oppId = user
                    oppUsers.add(oppId)
                }
            }
//            mDatabase.child("users").child("$oppId").addListenerForSingleValueEvent(object : ValueEventListener {
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

            for(chatList in chatLists) {
                if(chatList.chatUserIdx.toString()+"_key" == oppId) {
                    Glide.with(holder.itemView.context).load(chatList.profImg)
                    .apply(RequestOptions().circleCrop())
                    .into(holder.binding.charListProfileIv)

                    // nickname
                    holder.binding.chatListOppNameTv.text = chatList.nickname
                }
            }

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
//                binding.chatListOppNameTv.text = chatList.oppName
//                binding.chatListDate.text = chatList.date
//                binding.chatListContentTv.text = chatList.content
            }
        }
    }

}