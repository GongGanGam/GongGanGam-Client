package com.example.gonggangam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
//    lateinit var imm: InputMethodManager
//    lateinit var mDatabase: DatabaseReference
//    lateinit var chatModel: ChatModel
//    var me: User = User("lim", "", "1", "a")
//    var opp: User = User("kim", "", "2", "ab")
//    var chatRoomId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // init()
        // initListener()
    }
//
//    private fun init() {
//        mDatabase = FirebaseDatabase.getInstance().reference
//        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    }
//
//    private fun initListener() {
//        binding.chatBackIv.setOnClickListener {
//            finish()
//        }
//        binding.chatSendBtnIv.setOnClickListener {
//            chatModel = ChatModel()
//            chatModel.users[me.uid+"_key"] = true
//            chatModel.users[opp.uid+"_key"] = true
//
//            if(chatRoomId == null) {
//                Log.d("TAG_CHAT", "채팅방생성")
//                binding.chatSendBtnIv.isEnabled = false
//                mDatabase.child("chatRooms").push().setValue(chatModel).addOnSuccessListener{
//                    checkChatRoom()
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        sendMsg(binding.chatInputEt.text.toString())
//                    }, 1000L)
//                }
//            }
//            else {
//                sendMsg(binding.chatInputEt.text.toString())
//            }
//        }
//        // checkChatRoom()
//    }
//
//    private fun sendMsg(msg: String) {
//        if(msg != "") {
//            var comment = Comment(me.uid, msg, System.currentTimeMillis())
//            mDatabase.child("chatRooms").child(chatRoomId!!).child("comments").push().setValue(comment).addOnSuccessListener {
//                binding.chatInputEt.setText("")
//                Log.d("TAG_CHAT", "메세지전송완료")
//            }
//        }
//
//    }
//
//    private fun checkChatRoom() {
//        mDatabase.child("chatRooms").orderByChild("users/${me.uid}_key").equalTo(true).addListenerForSingleValueEvent(
//            object: ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    for(item in snapshot.children) {
//                        Log.d("TAG_CHAT, checkChatRoom", item.value.toString())
//                        var chatModel:ChatModel = item.getValue(ChatModel::class.java)!!
//                        Log.d("TAG_CHAT, checkChatRoom", chatModel.toString())
//                        if(chatModel?.users!!.containsKey(opp.uid+"_key")) {
//                            chatRoomId = item.key.toString()
//                            binding.chatSendBtnIv.isEnabled = true
//                            initRecyclerView()
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//
//            }
//        )
//    }
//
//    private fun initRecyclerView() {
//        binding.chatMessageRv.layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, false)
//        val chatRVAdapter = ChatRVAdapter()
//        binding.chatMessageRv.adapter = chatRVAdapter
//        Log.d("TAG_CHAT", "리사이클러뷰 초기화 함")
//    }
//
//    fun hideKeyboard(v: View) {
//        if(v != null) {
//            imm.hideSoftInputFromWindow(v.windowToken, 0)
//        }
//    }
//
//
//    inner class ChatRVAdapter: RecyclerView.Adapter<ChatRVAdapter.ViewHolder>() {
//        var comments = ArrayList<Comment>()
//        var user:User? = null
//        init {
//            Log.d("TAG_CHAT", "리싸이클러뷰 init 내부")
////            mDatabase.child("users").child(opp.uid+"_key").addListenerForSingleValueEvent(object:ValueEventListener{
////                override fun onDataChange(snapshot: DataSnapshot) {
////                    Log.d("TAG_CHAT initrv", snapshot.key.toString())
////                    //user = snapshot.getValue(User::class.java)!!
////                    getMessageList()
////                }
////
////                override fun onCancelled(error: DatabaseError) {
////                }
////
////            })
//            getMessageList()
//        }
//
//        fun getMessageList() {
//            mDatabase.child("chatRooms").child(chatRoomId!!).child("comments").addValueEventListener(object: ValueEventListener{
//                @SuppressLint("NotifyDataSetChanged")
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    comments.clear()
//                    for(item in snapshot.children) {
//                        val data = item.getValue(Comment::class.java)
//                        comments.add(data!!)
//                        Log.d("TAG_CHAT", comments.toString())
//                    }
//                    notifyDataSetChanged()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//            })
//        }
//
//        override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
//            val binding: ItemMessageBinding = ItemMessageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
//            return ViewHolder(binding)
//        }
//
//        override fun onBindViewHolder(holder: ChatRVAdapter.ViewHolder, position: Int) {
//            holder.bind(comments[position])
//        }
//
//        override fun getItemCount(): Int = comments.size
//
//        inner class ViewHolder(val b: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
//            fun bind(comment: Comment) {
//                if(comment.uid == me.uid+"_key") {
//                    // 내 메세지
//                    b.itemMessageOppNameTv.visibility = View.GONE
//                    b.itemMessageProfileIv.visibility = View.GONE
//                    b.itemMessageTv.text = comment.message
//                    b.itemMessageTv.setBackgroundResource(R.drawable.right_message_9)
//                }
//                else {
//                    // 상대 메세지
//                    b.itemMessageOppNameTv.visibility = View.VISIBLE
//                    b.itemMessageProfileIv.visibility = View.VISIBLE
//                    b.itemMessageOppNameTv.text = opp.name
//                    b.itemMessageTv.text = comment.message
//                    b.itemMessageTv.setBackgroundResource(R.drawable.left_message_9)
//                }
//            }
//        }
//
//    }

}