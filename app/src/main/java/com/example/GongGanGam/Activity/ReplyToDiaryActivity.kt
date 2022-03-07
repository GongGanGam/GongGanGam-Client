package com.example.gonggangam.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.example.gonggangam.Class.Diary
import com.example.gonggangam.DiaryService.DiaryRetrofitInterface
import com.example.gonggangam.DiaryService.Reply
import com.example.gonggangam.DiaryService.Response
import com.example.gonggangam.Fragment.ReceivedDiaryFragment
import com.example.gonggangam.R
import com.example.gonggangam.Util.ImageLoader
import com.example.gonggangam.databinding.ActivityReplyToDiaryBinding
import com.example.gonggangam.getJwt
import com.example.gonggangam.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback

class ReplyToDiaryActivity : AppCompatActivity() {
    lateinit var binding: ActivityReplyToDiaryBinding
    lateinit var diary: Diary
    private var readyToReply : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReplyToDiaryBinding.inflate(layoutInflater)
        diary = intent.getSerializableExtra("diary") as Diary
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
        diaryService.sendReply( getJwt(this), reply).enqueue(object: Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> Log.d("TAG/API-RESULT", resp.message)
                        else -> Log.d("TAG/API-RESULT", "답장 전송 실패" )
                    }
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("TAG/API-ERROR", t.message.toString())
            }

        })
    }

    private fun endReply () {
        val intent = Intent(this, ReceivedDiaryFragment::class.java)
        startActivity(intent)
    }
    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.replyToDiaryContentEt.windowToken, 0)
        binding.replyToDiaryContentEt.clearFocus()
    }

}