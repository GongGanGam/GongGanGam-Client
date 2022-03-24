package com.example.gonggangam.Activity

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
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
    lateinit var observer: MyLifecycleObserver
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        binding.writeDiaryPhotoIv.setImageURI(result.data?.data)
        binding.writeDiaryPhotoIv.visibility = View.VISIBLE
        binding.writeDiaryPhotoXBtn.visibility = View.VISIBLE
        binding.writeDiaryPhotoXBtn.setOnClickListener{
            //x 버튼 클릭했을 때
        }
    }

    var isShare:Boolean = false

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
        binding.writeMoodInfoTv.setText(str)
        binding.writeMoodIconIv.setImageBitmap(bitmap)
    }


    private fun initListener() {
        binding.writeSaveTv.setOnClickListener {
            //시연영상용 눈속임
//                Toast.makeText(this, "감정을 기록했습니다.", Toast.LENGTH_SHORT).show()
//                Handler(Looper.getMainLooper()).postDelayed({
//                    finish()
//                },2000) //1초가 1000mills
        }
        //위 writeSaveTv는 시연용입니다 적절하게 수정해주세요
        binding.writeBackIv.setOnClickListener {
            finish()
        }

        binding.writeDiaryBackCl.setOnClickListener {
            hideKeyBoard()
        }

        binding.writeUploadPhotoBtn.setOnClickListener {
            //이미지 첨부
            observer.selectImage()
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


        //retrofit 통신

        retrofit = RetrofitClient.getInstance()
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java) // 이렇게 사용하시면 됩니다.


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