package com.example.gonggangam.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gonggangam.model.ChatModel
import com.example.gonggangam.model.Comment
import com.example.gonggangam.model.User
import com.example.gonggangam.fragment.ChatFragment
import com.example.gonggangam.R
import com.example.gonggangam.util.ImageLoader
import com.example.gonggangam.util.PrefManager
import com.example.gonggangam.databinding.ActivityChatBinding
import com.example.gonggangam.databinding.ItemMessageLeftBinding
import com.example.gonggangam.databinding.ItemMessageRightBinding
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var imm: InputMethodManager
    lateinit var mDatabase: DatabaseReference
    lateinit var chatModel: ChatModel
    lateinit var bitmap:Bitmap
    var chatRoomId: String? = null

//    lateinit var opp: User // 상대 정보 nickname / userIdx/ profile
    var myUserIdx:Int = 0
    var me: User = User(8, "나", null)
    lateinit var opp: User
//    var opp:User = User("테스트", "https://gonggangam-bucket.s3.ap-northeast-2.amazonaws.com/btn_msg_blue.PNG", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myUserIdx = PrefManager.userIdx
        opp = intent.getSerializableExtra("opp") as User
        Log.d("opp",opp.toString())

        if(opp.profImg != null) {
            CoroutineScope(Dispatchers.Main).launch {
                bitmap = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(opp.profImg!!)!!
                }
            }
        }
        init()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        // initRecyclerView()
    }

    private fun init() {
        mDatabase = FirebaseDatabase.getInstance().reference
        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun initListener() {
        binding.chatBackIv.setOnClickListener {
            val intent = Intent(this@ChatActivity, ChatFragment::class.java)
            startActivity(intent)
            finish()
        }
        binding.chatSendBtnIv.setOnClickListener {
            // 메세지 전송
            chatModel = ChatModel()

            // 그냥 숫자로 저장하게 되면 파이어베이스에서 key로 인식하지 못함
            chatModel.users[myUserIdx.toString() +"_key"] = true
            chatModel.users[opp.uid.toString()+"_key"] = true
//            chatModel.opp[opp.uid.toString()+"_key"] = opp // 상대 추가

            // 채팅방 아이디 없으면
            if(chatRoomId == null) {
                Log.d("TAG_CHAT", "채팅방생성")
                binding.chatSendBtnIv.isEnabled = false
                mDatabase.child("chatRooms").push().setValue(chatModel).addOnSuccessListener{
                    checkChatRoom()
                    Handler(Looper.getMainLooper()).postDelayed({
                        sendMsg(binding.chatInputEt.text.toString())
                    }, 1000L)
                }
            }
            else { // 채팅방 아이디 존재하면
                sendMsg(binding.chatInputEt.text.toString())
            }
            // checkChatRoom()
        }

        binding.chatMenuIv.setOnClickListener {
            goToReportActivity()
        }
    }

    private fun sendMsg(msg: String) {
        if(msg != "") {
            var comment = Comment(myUserIdx.toString(), msg, System.currentTimeMillis())
            mDatabase.child("chatRooms").child(chatRoomId!!).child("comments").push().setValue(comment).addOnSuccessListener {
                binding.chatInputEt.setText("")
                Log.d("TAG_CHAT", "메세지전송완료")
            }
        }

    }

    private fun checkChatRoom() {
        mDatabase.child("chatRooms").orderByChild("users/${myUserIdx.toString()}_key").equalTo(true).addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(item in snapshot.children) {
                        Log.d("TAG_CHAT, checkChatRoom", item.value.toString())
                        var chatModel: ChatModel = item.getValue(ChatModel::class.java)!!
                        Log.d("TAG_CHAT, checkChatRoom", chatModel.toString())
                        if(chatModel?.users!!.containsKey(opp.uid.toString()+"_key")) {
                            chatRoomId = item.key.toString()
                            binding.chatSendBtnIv.isEnabled = true

                            // 리사이클러뷰 초기화 메세지 읽어들이기
                            initRecyclerView()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )
    }

    private fun initRecyclerView() {
        binding.chatMessageRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val chatRVAdapter = ChatRVAdapter()
        binding.chatMessageRv.adapter = chatRVAdapter
//        chatRVAdapter.addMessage(test)
////        mDatabase.child("chatRooms").child(chatRoomId!!).child("comments").addValueEventListener(object: ValueEventListener{
////            override fun onDataChange(snapshot: DataSnapshot) {
////                var comments = ArrayList<Comment>()
////                for(item in snapshot.children) {
////                    val comment = item.getValue(Comment::class.java)
////                    comments.add(comment!!)
////                }
////                chatRVAdapter.addMessage(comments)
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////
////            }
////        })
    }

    private fun goToReportActivity() {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra("reportType", "chat")
        intent.putExtra("idxOfType", 0)
        // nickname putExtra
        startActivity(intent)
    }

    fun hideKeyboard(v: View) {
        if(v != null) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTimestampToDate(timestamp: Long): String? {
        val sdf = SimpleDateFormat("HH:mm")
        return sdf.format(timestamp) as String
    }

    inner class ChatRVAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var comments = ArrayList<Comment>()
        var user: User? = null
        init {
            Log.d("TAG_CHAT", "리싸이클러뷰 init 내부")
            mDatabase.child("users").child(opp.uid.toString()+"_key").addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("TAG_CHAT", snapshot.key.toString())
                    //user = snapshot.getValue(User::class.java)!!
                    comments.add(Comment(opp.uid.toString()!!, "안녕하세요! 일기가 정말 인상깊어서 꼭 이야기 나누고 싶었어요", System.currentTimeMillis()))
                    getMessageList()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }

        fun getMessageList() {
            mDatabase.child("chatRooms").child(chatRoomId!!).child("comments").addValueEventListener(object: ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    comments.clear()
                    for(item in snapshot.children) {
                        val data = item.getValue(Comment::class.java)
                        comments.add(data!!)
                        Log.d("TAG_CHAT", comments.toString())
                    }
                    notifyDataSetChanged()
                    binding.chatMessageRv.scrollToPosition(comments.size-1)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        @SuppressLint("NotifyDataSetChanged")
        fun addMessage(comments: ArrayList<Comment>) {
            this.comments.clear()
            this.comments.addAll(comments)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val bindingLeft: ItemMessageLeftBinding = ItemMessageLeftBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            val bindingRight: ItemMessageRightBinding = ItemMessageRightBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return if(viewType == 0) {
                ViewHolderRight(bindingRight)
            } else {
                ViewHolder(bindingLeft)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
           //  holder.bind(comments[position])
            if(getItemViewType(position) == 0) {
                (holder as ViewHolderRight).bind(comments[position])
            } else {
                (holder as ViewHolder).bind(comments[position])
            }
        }

        override fun getItemCount(): Int = comments.size

        override fun getItemViewType(position: Int): Int {
            return if(comments[position].uid+"_key" == myUserIdx.toString()+"_key") {
                0 // 오른쪽
            } else {
                1 // 왼
            }
        }

        inner class ViewHolder(private val b: ItemMessageLeftBinding) : RecyclerView.ViewHolder(b.root) {
            fun bind(comment: Comment) {
                // 상대 프로필 이미지
                if(opp.profImg == null) {
                    b.itemMessageLeftProfileIv.setImageResource(R.drawable.default_profile_img)
                }
                else { // bitmap을 최초 1번 생성 후 glide apply
                    Glide.with(this@ChatActivity).load(bitmap).circleCrop().into(b.itemMessageLeftProfileIv)
                }
//                else {
//
//                    CoroutineScope(Dispatchers.Main).launch {
//                        val bitmap = withContext(Dispatchers.IO) {
//                            ImageLoader.loadImage(opp.profImg!!)
//                        }
//                        // Glide 적용
//                        Glide.with(this@ChatActivity).load(bitmap).circleCrop().into(b.itemMessageLeftProfileIv)
//                    }
//                }

                b.itemMessageLeftNameTv.text = opp.nickname // 상대 이름
                b.itemMessageLeftTv.text = comment.message // 상대 메세지
                b.itemMessageLeftTimeTv.text = convertTimestampToDate(comment.timeStamp)
            }

        }

        inner class ViewHolderRight(private val b: ItemMessageRightBinding) : RecyclerView.ViewHolder(b.root) {
            fun bind(comment: Comment) {
                b.itemMessageRightTv.text = comment.message
                var time = convertTimestampToDate(comment.timeStamp)
                b.itemMessageRightTimeTv.text = convertTimestampToDate(comment.timeStamp)
            }

        }
    }

}