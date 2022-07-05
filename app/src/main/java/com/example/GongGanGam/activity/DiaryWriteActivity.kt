package com.example.GongGanGam.activity

import android.Manifest
import android.annotation.SuppressLint
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
import com.example.GongGanGam.diaryService.BasicResponse
import com.example.GongGanGam.diaryService.DiaryRetrofitInterface
import com.example.GongGanGam.diaryService.WriteDiary
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityDiaryWriteBinding
import com.example.GongGanGam.util.getRetrofit
import com.example.gonggangam.activity.DiaryWriteEmojiActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DiaryWriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryWriteBinding
    lateinit var observer: MyLifecycleObserver
    val PERMISSIONS_GALLERY_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE)
    var mBackWait:Long = 0 //뒤로가기 버튼 눌렀을 때

    var isShare:Boolean = false
    // lateinit var diaryImg:String // 세영님 요청하신 diaryImg uri 저장 변수
    var diaryImg:String = ""

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
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
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
        val emojiStr=intent.getStringExtra("state")
        Log.d("이모지?", emojiStr.toString())
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
        var emojiStr=intent.getStringExtra("state").toString()
        when (emojiStr) {
            "우울해요" -> emojiStr = "depressed"
            "짜증나요" -> emojiStr ="annoyed"
            "지루해요" -> emojiStr ="boring"
            "복잡해요" -> emojiStr ="complicated"
            "창피해요" -> emojiStr ="embarrassing"
            "설레요" -> emojiStr ="excited"
            "즐거워요" ->  emojiStr ="fun"
            "행복해요" -> emojiStr ="happy"
            "슬퍼요" ->  emojiStr ="sad"
            "그냥 그래요" ->  emojiStr ="soso"
            "화나요" ->  emojiStr ="upset"
            "궁금해요" ->   emojiStr ="wonder"
        }
        Log.d("이모지?", emojiStr)
        val year = intent.getIntExtra("year",0)
        val month = intent.getIntExtra("month",0)
        val day = intent.getIntExtra("day",0)

        val content = binding.writeInputEt.text.toString()
        val shareAgree = if (isShare) "T" else "F"

        if(content.isEmpty()){
            Toast.makeText(this, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show()
            return
        }

        var imgBody : MultipartBody.Part? = null
        if(diaryImg!=""){

            val img = File(diaryImg)
            val imgFile = RequestBody.create("image/jpg".toMediaTypeOrNull(), img)
            imgBody = MultipartBody.Part.createFormData("uploadImg", img.name, imgFile)
        }
        val writeD = WriteDiary(emojiStr,year,month,day,content, shareAgree,imgBody)

        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java) // 이렇게 사용하시면 됩니다.

        diaryService.diaryWrite(writeD).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                Log.d("글", "저장하려고")

                if (response.isSuccessful) { //  response.code == 1000

                    Log.d("Retrofit", "onResponse 성공")
                    Log.d("Retrofit", writeD.toString())

                } else { //  response.code == 2000,3000,5001,5002
                    Log.d("Retrofit", "onResponse 실패"+response.code())
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                // 실패 처리
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("Retrofit", "onFailure 에러: "+ t.message.toString())
                return
            }
        })
        Toast.makeText(this, "일기가 저장되었습니다", Toast.LENGTH_LONG).show()
        finish()


    }

    @SuppressLint("SetTextI18n")
    private fun initListener() {
        binding.writeSaveTv.setOnClickListener {
            saveDiary()
        }

        binding.writeHeaderTitleTv.text = "${ intent.getIntExtra("year",0) }년 ${intent.getIntExtra("month",0)}월 ${intent.getIntExtra("day",0)}일 "

        binding.writeBackIv.setOnClickListener {
            if(System.currentTimeMillis() - mBackWait >=2000 ) {
                mBackWait = System.currentTimeMillis()
                Toast.makeText(this, "한 번 더 누르면 일기 작성을 종료합니다.", Toast.LENGTH_SHORT).show()
            } else {
                finish() //액티비티 종료
            }
        }

        if(intent.getStringExtra("content")?.isNotEmpty() == true) {
            binding.writeInputEt.setText(intent.getStringExtra("content"))
        }

        if(intent.getStringExtra("shareAgree")?.isNotEmpty() == true) {
            if(intent.getStringExtra("shareAgree")=="T") {
                binding.writeShareBtn.setBackgroundResource(R.drawable.write_share_btn_active)
                binding.writeShareCheckIv.visibility = View.VISIBLE
                isShare=true
            }
            else {
                binding.writeShareBtn.setBackgroundResource(R.drawable.write_share_btn_inactive)
                binding.writeShareCheckIv.visibility = View.GONE
            }
        }
        if(intent.getStringExtra("img")?.isNotEmpty() == true) {
            // Glide로 띄우기, 모서리 조정
            Glide.with(this).load(intent.getStringExtra("img"))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.writeDiaryPhotoIv)

            // 이미지 visibility handling
            binding.writeDiaryPhotoIv.visibility = View.VISIBLE
            binding.writeDiaryPhotoXBtn.visibility = View.VISIBLE

            diaryImg=intent.getStringExtra("img").toString()
        }

        binding.writeDiaryBackCl.setOnClickListener {
            hideKeyBoard()
        }

        binding.writeUploadPhotoBtn.setOnClickListener {
            // 갤러리 권한 체크
            requestPermission()
        }

        binding.writeDiaryPhotoXBtn.setOnClickListener {
            //x 버튼 클릭했을 때
            binding.writeDiaryPhotoIv.setImageURI(null)
            binding.writeDiaryPhotoIv.visibility = View.GONE
            binding.writeDiaryPhotoXBtn.visibility = View.GONE
            diaryImg=""
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

        binding.writeMoodChangeBtn.setOnClickListener {
            val year=intent.getIntExtra("year",0)
            val month = intent.getIntExtra("month",0)
            val day = intent.getIntExtra("day",0)
            Log.d("이어",year.toString())
            Log.d("diaryImg",diaryImg.toString())
            val shareAgree = if (isShare) "T" else "F"
            val emojiIntent = Intent(this, DiaryWriteEmojiActivity::class.java)
            emojiIntent.putExtra("year",year)
            emojiIntent.putExtra("month",month)
            emojiIntent.putExtra("day",day)
            emojiIntent.putExtra("content",binding.writeInputEt.text.toString())
            emojiIntent.putExtra("shareAgree",shareAgree)
            emojiIntent.putExtra("img",diaryImg)
            startActivity(emojiIntent)
            finish()
        }

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