package com.example.gonggangam

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.databinding.ActivityDiaryWriteBinding


class DiaryWriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryWriteBinding
    var isShare:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryWriteBinding.inflate(layoutInflater)

        getImoji()
        initListener()
        setContentView(binding.root)
    }

    private fun getImoji(){
        val extras = intent.extras
        val s = extras!!.getString("state")
        val byteArray = intent.getByteArrayExtra("image")
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

        val str = """
            $s
            
            """.trimIndent()
        binding.writeMoodInfoTv.setText(str)
        binding.writeMoodIconIv.setImageBitmap(bitmap)
    }

    private fun initListener() {
        binding.writeBackIv.setOnClickListener {
            finish()
        }

        binding.writeDiaryBackCl.setOnClickListener {
            hideKeyBoard()
        }

        binding.writeUploadPhotoBtn.setOnClickListener {
            //이미지 첨부
        }

        binding.writeShareBtn.setOnClickListener {
            isShare = !isShare
            if(!isShare) {
                binding.writeShareBtn.setBackgroundResource(R.drawable.write_share_btn_active)
                binding.writeShareCheckIv.visibility = View.VISIBLE
            }
            else {
                binding.writeShareBtn.setBackgroundResource(R.drawable.write_share_btn_inactive)
                binding.writeShareCheckIv.visibility = View.GONE
            }
        }


    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.writeInputEt.windowToken, 0)
    }


}