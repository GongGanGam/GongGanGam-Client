package com.example.GongGanGam.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.GongGanGam.diaryService.DiaryRetrofitInterface
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityDiaryWriteBinding
import com.example.GongGanGam.util.getRetrofit
import retrofit2.Retrofit


class DiaryWriteActivity : AppCompatActivity() {
    val PERMISSIONS_GALLERY_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE)

    lateinit var binding: ActivityDiaryWriteBinding

    private lateinit var retrofit: Retrofit

    var isShare:Boolean = false
    lateinit var diaryImg:String // 세영님 요청하신 diaryImg uri 저장 변수

    lateinit var observer: MyLifecycleObserver

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSIONS_GALLERY_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허용 되었으면
                    //이미지 첨부
                    observer.selectImage()
                }
                else {
                    // 권한 거부 되었으면
                    Toast.makeText(this, "갤러리 접근 권한 거부됨", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun requestPermission(){
        var permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            //설명이 필요한지
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //설명 필요 (사용자가 요청을 거부한 적이 있음)
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_GALLERY_CODE )
            }else{
                //설명 필요하지 않음
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_GALLERY_CODE )
            }
        }else{
            //권한 허용
            observer.selectImage()
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {

            // 이미지 uri 넣기
            diaryImg = result.data?.data.toString() // image uri 저장
            Log.d("TAG_WRITE_RESULT", diaryImg)

            // Glide로 띄우기, 모서리 조정
            Glide.with(this).load(result.data?.data)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.writeDiaryPhotoIv)

            // 이미지 visibility handling
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
        // 첨부한 이미지 uri string은 diaryImg 입니다! 코드를 살펴보면 이해가실겁니다:)

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
            requestPermission()
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