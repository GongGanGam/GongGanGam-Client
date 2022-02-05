package com.example.GongGanGam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.GongGanGam.databinding.ActivityChatBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var mDatabase: DatabaseReference
    lateinit var chatModel: ChatModel
    var me: User = User("lim", "", "1", "a")
    var opp: User = User("kim", "", "2", "ab")
    var chatRoomId:String = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
        initListener()
    }

    private fun initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    private fun initListener() {
        binding.chatBackIv.setOnClickListener {
            finish()
        }
        binding.chatSendBtnIv.setOnClickListener {
            chatModel = ChatModel()
            chatModel.users[me.uid] = true
            chatModel.users[opp.uid] = true

            if(chatRoomId == "") {
                Log.d("chat", "채팅방생")
                mDatabase.ref.child("chatRooms").push().setValue(chatModel).addOnSuccessListener{
                    checkChatRoom()
                }


            }
            else {
                sendMsg(binding.chatInputEt.text.toString())
            }
        }
    }

    private fun sendMsg(msg: String) {
        if(msg != "") {
            var comment = chatModel.Comment()
            comment.uid = me.uid
            comment.message = msg
            comment.timeStamp = System.currentTimeMillis()
            mDatabase.child("chatRooms").child(chatRoomId).child("comments").push().setValue(comment).addOnSuccessListener {
                binding.chatInputEt.setText("")
            }
        }

    }

    private fun checkChatRoom() {
        mDatabase.ref.child("chatRooms").orderByChild("users/"+me.uid).equalTo(true).addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(data in snapshot.children) {
                        chatModel = data.getValue(ChatModel::class.java)!!
                        if(chatModel.users.containsKey(opp.uid)) {
                            chatRoomId = data.key.toString()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("chat", "채팅방 체크 취")
                }

            }
        )


    }
}
