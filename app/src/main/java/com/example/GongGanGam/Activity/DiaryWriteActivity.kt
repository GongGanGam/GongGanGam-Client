package com.example.gonggangam.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.gonggangam.DiaryService.DiaryRetrofitInterface
import com.example.gonggangam.R
import com.example.gonggangam.RetrofitClient
import com.example.gonggangam.databinding.ActivityDiaryWriteBinding
import com.example.gonggangam.getRetrofit
import retrofit2.Retrofit


class DiaryWriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryWriteBinding
    private lateinit var retrofit: Retrofit

    var isShare:Boolean = false

    lateinit var observer: MyLifecycleObserver
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {

            // 이미지 uri 넣기
            binding.writeDiaryPhotoIv.setImageURI(result.data?.data)
            Log.d("TAG_WRITE_RESULT", result.data?.data.toString())

            // 이미지 띄우기
            binding.writeDiaryPhotoIv.visibility = View.VISIBLE
            binding.writeDiaryPhotoXBtn.visibility = View.VISIBLE
        }

        else {
            Log.d("TAG_WRITE_ERROR", "이미지 불러오기 취소")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryWriteBinding.inflate(layoutInflater)
        observer = MyLifecycleObserver(this.activityResultRegistry)
        lifecycle.addObserver(observer)


        initListener()
        getImoji()
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
        binding.writeMoodInfoTv.text = str
        binding.writeMoodIconIv.setImageBitmap(bitmap)
    }

    private fun saveDiary() {
        // 다이어리 저장 api 불러오면 됩니다!
        // 레트로핏 통신 진행

        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java) // 이렇게 사용하시면 됩니다.
    }

    private fun initListener() {
        binding.writeSaveTv.setOnClickListener {
            saveDiary()
        }
        //위 writeSaveTv는 시연용입니다 적절하게 수정해주세요
        binding.writeBackIv.setOnClickListener {
            finish()
        }

        binding.writeDiaryBackCl.setOnClickListener {
            hideKeyBoard()
        }

        binding.writeUploadPhotoBtn.setOnClickListener {
            // 갤러리 권한 체크

            //이미지 첨부
            observer.selectImage()
        }

        binding.writeDiaryPhotoXBtn.setOnClickListener{
            //x 버튼 클릭했을 때
            binding.writeDiaryPhotoIv.setImageURI(null)
            binding.writeDiaryPhotoIv.visibility = View.GONE
            binding.writeDiaryPhotoXBtn.visibility = View.GONE
        }

        binding.writeShareBtn.setOnClickListener {
            if(!isShare) {
                binding.writeShareBtn.setBackgroundResource(R.drawable.write_share_btn_active)
                binding.writeShareCheckIv.visibility = View.VISIBLE
            }
            else {
                binding.writeShareBtn.setBackgroundResource(R.drawable.write_share_btn_inactive)
                binding.writeShareCheckIv.visibility = View.GONE
            }
            isShare = !isShare
        }

        binding.writeInputEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.writeInputNv.invalidate()
                binding.writeInputNv.requestLayout()
            }
        })


//
//            val emojiRequestBody : RequestBody = emoji.toPlainRequestBody()
//            val yearRequestBody: RequestBody = year.toPlainRequestBody()
//            val monthRequestBody: RequestBody = month.toPlainRequestBody()
//            val dayRequestBody: RequestBody = day.toPlainRequestBody()
//            val contentRequestBody: RequestBody = content.toPlainRequestBody()
//            val textHashMap = hashMapOf<java.lang.String, RequestBody>()
//            textHashMap["emoji"] = emojiRequestBody
//            textHashMap["year"] = yearRequestBody
//            textHashMap["month"] = monthRequestBody
//            textHashMap["day"] = dayRequestBody
//            textHashMap["content"] = indexRequestBody
//
//
//            service.getCalendar(Year.now().value.toInt(), YearMonth.now().monthValue.toInt())
//                .enqueue((object : Callback<DayResponse> {
//                    override fun onResponse(
//                        call: Call<DayResponse>,
//                        response: Response<DayResponse>
//                    ) {
//                        if (response.isSuccessful) { //  response.code == 1000
//                            // 성공 처리
//                            val temp = response?.body()!!.result
//
//                            for (i in temp) {
//                                dayList?.set(i.day.toInt(), i)
//                            }
//
//                            Log.d("Retrofit", "onResponse 성공")
//                            Log.d("됨", "onResponse 성공")
//                            Log.d("년 달",
//                                Year.now().value.toInt().toString() + YearMonth.now().monthValue.toInt()
//                                    .toString()
//                            )
//                        } else { //  response.code == 2000,3000,5001,5002
//                            Log.d("Retrofit", "onResponse 실패" + response.code())
//                            Log.d("안됨", "onResponse 실패" + response.code())
//                        }
//                    }
//
//                    override fun onFailure(call: Call<DayResponse>, t: Throwable) {
//                        // 실패 처리
//                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
//                        Log.d("Retrofit", "onFailure 에러: " + t.message.toString())
//                    }
//                }))



    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.writeInputEt.windowToken, 0)
        binding.writeInputEt.clearFocus()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        getContent.launch(intent)
    }

    inner class MyLifecycleObserver(private val registry: ActivityResultRegistry)
        : DefaultLifecycleObserver {
        lateinit var getContent : ActivityResultLauncher<String>

        override fun onCreate(owner: LifecycleOwner) {
            getContent = registry.register("key", owner, ActivityResultContracts.GetContent()) { uri ->
                // Handle the returned Uri
            }
        }

        fun selectImage() {
            openGallery()
        }
    }
}