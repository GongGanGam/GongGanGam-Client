package com.example.gonggangam.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gonggangam.R
import com.example.gonggangam.activity.ChatActivity
import com.example.gonggangam.diaryService.ChatListResponse
import com.example.gonggangam.diaryService.DiaryRetrofitInterface
import com.example.gonggangam.databinding.FragmentChatBinding
import com.example.gonggangam.databinding.ItemChatListBinding
import com.example.gonggangam.model.*
import com.example.gonggangam.util.BindingAdapter
import com.example.gonggangam.util.PrefManager
import com.example.gonggangam.util.getRetrofit
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
    lateinit var oppParam: User
    private var chatLists = ArrayList<ChatList>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        mDatabase = FirebaseDatabase.getInstance().reference

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        binding.chatRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val chatListRVAdapter = ChatListRVAdapter()
        binding.chatRv.adapter = chatListRVAdapter
    }

    private fun goToChat(opp:User,index:String) {
        val intent = Intent(activity, ChatActivity::class.java)
        intent.putExtra("opp",opp)
        intent.putExtra("chatRoomId",index)
        startActivity(intent)
    }

    private fun getChatList() {
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        diaryService.getChatList().enqueue(object: Callback<ChatListResponse> {
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
        private val uid : Int = PrefManager.userIdx

        init {
            getChatList()

            mDatabase.child("chatRooms").orderByChild("users/${uid}_key").equalTo(true).addListenerForSingleValueEvent(object :
                ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    for(item in snapshot.children) {
//                        Log.d("TAG_CHAT, checkChatRoom", item.value.toString())
//                        var chatModel: ChatModel = item.getValue(ChatModel::class.java)!!
//                        Log.d("TAG_CHAT, checkChatRoom", chatModel.toString())
//                        if(chatModel?.users!!.containsKey(opp.uid.toString()+"_key")) {
//                            chatRoomId = item.key.toString()
//                            binding.chatSendBtnIv.isEnabled = true
//
//                            // 리사이클러뷰 초기화 메세지 읽어들이기
//                            initRecyclerView()
//                        }
//                    }
//                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatModel.clear()
                    for(data in snapshot.children) {
                        chatModel.add(data.getValue<ChatModel>()!!)
                        println(data)
                    }
                    notifyDataSetChanged()
                    checkEmpty()
                }
                override fun onCancelled(error: DatabaseError) {
                }

            })
            checkEmpty()
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
            for((index,chatList) in chatLists.withIndex()) {
                if(chatList.chatUserIdx.toString()+"_key" == oppId) {
                    BindingAdapter.loadProfileImage(
                        chatList.profImg,
                        holder.binding.charListProfileIv,
                        ContextCompat.getDrawable(requireContext(), R.drawable.default_profile_img)!!
                    )
                    // nickname
                    holder.binding.chatListOppNameTv.text = chatList.nickname
                    holder.binding.chatListCl.setOnClickListener {
                        var tmpUser= User(chatList.chatUserIdx, chatList.nickname,  chatList.profImg.toString())
                        goToChat(tmpUser,index.toString())
                    }
                }
            }

            // last message & time
            val commentMap = TreeMap<String, Comment>(reverseOrder())
            commentMap.putAll(chatModel[position].comments)
            val lastMessageKey = commentMap.keys.toTypedArray()[commentMap.keys.size-1]
            holder.binding.chatListContentTv.text = chatModel[position].comments[lastMessageKey]?.message
            holder.binding.chatListDate.text = convertTimestampToDate(chatModel[position].comments[lastMessageKey]?.timeStamp!!)

//            holder.binding.chatListCl.setOnClickListener {
//                goToChat()
//            }
        }

        override fun getItemCount(): Int {
            return chatModel.size
        }

        inner class ViewHolder(val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {

        }

        private fun checkEmpty() {
            if (chatLists.isEmpty() && chatModel.isEmpty())
                binding.tvEmptyList.visibility = View.VISIBLE
            else
                binding.tvEmptyList.visibility = View.INVISIBLE
        }
    }

}