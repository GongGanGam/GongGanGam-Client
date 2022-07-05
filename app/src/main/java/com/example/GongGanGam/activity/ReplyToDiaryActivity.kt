package com.example.GongGanGam.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.example.GongGanGam.model.ReceivedDiary
import com.example.GongGanGam.diaryService.BasicResponse
import com.example.GongGanGam.diaryService.DiaryRetrofitInterface
import com.example.GongGanGam.diaryService.Reply
import com.example.gonggangam.R
import com.example.GongGanGam.util.ImageLoader
import com.example.gonggangam.databinding.ActivityReplyToDiaryBinding
import com.example.GongGanGam.util.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback

class ReplyToDiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityReplyToDiaryBinding
    var diary = ReceivedDiary()
    private var readyToReply : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReplyToDiaryBinding.inflate(layoutInflater)
        diary = intent.getSerializableExtra("diary") as ReceivedDiary
        initListener()

        setContentView(binding.root)
    }

    private fun initListener() {
        binding.replyToDiaryBackCl.setOnClickListener {
            hideKeyBoard()
        }

        // set target data
        binding.replyToDiaryTargetNameTv.text = diary.userNickname
        if(diary.userProfImg == null) {
            binding.replyToDiaryProfileIv.setImageResource(R.drawable.default_profile_img)
        }
        else {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = withContext(Dispatchers.IO) {
                    ImageLoader.loadImage(diary.userProfImg!!)
                }
                binding.replyToDiaryProfileIv.setImageBitmap(bitmap)
            }
        }
        binding.replyToDiaryContentEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                readyToReply = binding.replyToDiaryContentEt.text.toString().isNotEmpty()
                if(readyToReply) { // 보낼 준비 완료
                    binding.replyToDiaryBtn.setBackgroundResource(R.drawable.button_active_background)
                    binding.replyToDiaryBtnTv.setTextColor(resources.getColor(R.color.primaryColor))
                    binding.replyToDiaryBtn.isEnabled = true
                }
                else { // 보내면 안됨
                    binding.replyToDiaryBtn.setBackgroundResource(R.drawable.button_inactive_background)
                    binding.replyToDiaryBtnTv.setTextColor(resources.getColor(R.color.inactiveBtnColor))
                    binding.replyToDiaryBtn.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.replyToDiaryBtn.setOnClickListener {
            sendReply()
        }

        binding.replyToDiaryBackIv.setOnClickListener {
            finish()
        }
    }

    private fun sendReply() {
        // send diary api
        val reply = Reply(binding.replyToDiaryContentEt.text.toString(), diary.userIdx!!)
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        diaryService.sendReply(reply).enqueue(object: Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: retrofit2.Response<BasicResponse>) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> {
                            Log.d("TAG/API-RESULT", resp.message)
                            finish()
                        }
                        else -> Log.d("TAG/API-RESULT", "답장 전송 실패" )
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("TAG/API-ERROR", t.message.toString())
            }

        })
    }

    private fun endReply () {
        val intent = Intent(this, ReplyToDiaryActivity::class.java)
        startActivity(intent)
    }
    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.replyToDiaryContentEt.windowToken, 0)
        binding.replyToDiaryContentEt.clearFocus()
    }

}