package com.example.gonggangam.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
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
import androidx.core.net.toUri
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.gonggangam.diaryService.BasicResponse
import com.example.gonggangam.diaryService.DiaryRetrofitInterface
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityDiaryWriteBinding
import com.example.gonggangam.diaryService.ReadDiary
import com.example.gonggangam.model.Diary
import com.example.gonggangam.util.BindingAdapter
import com.example.gonggangam.util.FormDataUtil
import com.example.gonggangam.util.getRetrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DiaryWriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryWriteBinding
    lateinit var observer: MyLifecycleObserver
    val PERMISSIONS_GALLERY_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE)
    var mBackWait:Long = 0 //???????????? ?????? ????????? ???

    private lateinit var imgUri: Uri // diaryImg uri ?????? ??????
    var isShare:Boolean = false

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSIONS_GALLERY_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // ?????? ?????? ????????????
                    //????????? ??????
                    observer.selectImage()
                }
                else {
                    // ?????? ?????? ????????????
                    Toast.makeText(this, "????????? ?????? ?????? ?????????", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun requestPermission(){
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            //????????? ????????????
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //?????? ?????? (???????????? ????????? ????????? ?????? ??????)
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_GALLERY_CODE )
            }else{
                //?????? ???????????? ??????
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_GALLERY_CODE )
            }
        }else{
            //?????? ??????
            observer.selectImage()
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {

            // ????????? uri ??????
            imgUri = result.data?.data!! // image uri ??????

            // Glide??? ?????????, ????????? ??????
            Glide.with(this).load(imgUri)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.writeDiaryPhotoIv)

            // ????????? visibility handling
            binding.writeDiaryPhotoIv.visibility = View.VISIBLE
            binding.writeDiaryPhotoXBtn.visibility = View.VISIBLE
        }

        else {
            Log.d("TAG_WRITE_ERROR", "????????? ???????????? ??????")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryWriteBinding.inflate(layoutInflater)
        observer = MyLifecycleObserver(this.activityResultRegistry)
        lifecycle.addObserver(observer)

        initListener()
        loadIntent()
        getImoji()
        val emojiStr=intent.getStringExtra("state")
        Log.d("??????????", emojiStr.toString())
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
            "????????????" -> emojiStr = "depressed"
            "????????????" -> emojiStr ="annoyed"
            "????????????" -> emojiStr ="boring"
            "????????????" -> emojiStr ="complicated"
            "????????????" -> emojiStr ="embarrassing"
            "?????????" -> emojiStr ="excited"
            "????????????" ->  emojiStr ="fun"
            "????????????" -> emojiStr ="happy"
            "?????????" ->  emojiStr ="sad"
            "?????? ?????????" ->  emojiStr ="soso"
            "?????????" ->  emojiStr ="upset"
            "????????????" ->   emojiStr ="wonder"
        }
        Log.d("??????????", emojiStr)
        val year = intent.getIntExtra("year",0)
        val month = intent.getIntExtra("month",0)
        val day = intent.getIntExtra("day",0)

        val content = binding.writeInputEt.text.toString()
        val shareAgree = if (isShare) "T" else "F"

        if(content.isEmpty()){
            Toast.makeText(this, "????????? ??????????????????!", Toast.LENGTH_SHORT).show()
            return
        }

        // image
        val realPath = FormDataUtil.getRealPathFromURI(imgUri, this)
        val destFile = File(realPath)
        if(!destFile.exists())
            destFile.mkdirs()
        val requestFile = destFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imgRequestBody = MultipartBody.Part.createFormData("uploadImg", destFile.name, requestFile)

        // ????????? body
        val emojiRequestBody : RequestBody = emojiStr.toRequestBody()
        val yearRequestBody: RequestBody = year.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val monthRequestBody: RequestBody = month.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val dayRequestBody: RequestBody = day.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val contentRequestBody: RequestBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val shareRequestBody: RequestBody = shareAgree.toRequestBody("text/plain".toMediaTypeOrNull())

        val textHashMap = hashMapOf<String, RequestBody>()

        textHashMap["emoji"] = emojiRequestBody
        textHashMap["year"] = yearRequestBody
        textHashMap["month"] = monthRequestBody
        textHashMap["day"] = dayRequestBody
        textHashMap["content"] = contentRequestBody
        textHashMap["shareAgree"] = shareRequestBody

        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)

        diaryService.writeDiary(imgRequestBody, textHashMap).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                Log.d("???", "???????????????")

                if (response.isSuccessful) { //  response.code == 1000
                    Log.d("Retrofit", "onResponse ??????")

                } else { //  response.code == 2000,3000,5001,5002
                    Log.d("Retrofit", "onResponse ??????"+response.code())
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                // ?????? ??????
                // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                Log.d("Retrofit", "onFailure ??????: "+ t.message.toString())
                return
            }
        })
        Toast.makeText(this, "????????? ?????????????????????", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun loadIntent() {
        val diary = intent.getSerializableExtra("diary") as ReadDiary?

        if (diary != null) {
            BindingAdapter.loadEmoji(diary.emoji, binding.writeMoodIconIv)
            binding.writeInputEt.setText(diary.contents)

            if (diary.image != null) {
                BindingAdapter.loadDiaryImage(diary.image, binding.writeDiaryPhotoIv)
                binding.writeDiaryPhotoIv.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initListener() {
        binding.writeSaveTv.setOnClickListener {
            saveDiary()
        }

        binding.writeHeaderTitleTv.text = "${ intent.getIntExtra("year",0) }??? ${intent.getIntExtra("month",0)}??? ${intent.getIntExtra("day",0)}??? "

        binding.writeBackIv.setOnClickListener {
            if(System.currentTimeMillis() - mBackWait >=2000 ) {
                mBackWait = System.currentTimeMillis()
                Toast.makeText(this, "??? ??? ??? ????????? ?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show()
            } else {
                finish() //???????????? ??????
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

        if(!intent.getStringExtra("img").isNullOrEmpty()) {
            // Glide??? ?????????, ????????? ??????
            Glide.with(this).load(intent.getStringExtra("img"))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.writeDiaryPhotoIv)

            // ????????? visibility handling
            binding.writeDiaryPhotoIv.visibility = View.VISIBLE
            binding.writeDiaryPhotoXBtn.visibility = View.VISIBLE

            imgUri= intent.getStringExtra("img")!!.toUri()
        }

        binding.writeDiaryBackCl.setOnClickListener {
            hideKeyBoard()
        }

        binding.writeUploadPhotoBtn.setOnClickListener {
            // ????????? ?????? ??????
            requestPermission()
        }

        binding.writeDiaryPhotoXBtn.setOnClickListener {
            //x ?????? ???????????? ???
            binding.writeDiaryPhotoIv.setImageURI(null)
            binding.writeDiaryPhotoIv.visibility = View.GONE
            binding.writeDiaryPhotoXBtn.visibility = View.GONE
            imgUri = "".toUri()
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

        // TODO : ?????? ????????? ?????? & ??????
        binding.writeMoodChangeBtn.setOnClickListener {
            val year=intent.getIntExtra("year",0)
            val month = intent.getIntExtra("month",0)
            val day = intent.getIntExtra("day",0)
            val shareAgree = if (isShare) "T" else "F"
            val emojiIntent = Intent(this, DiaryWriteEmojiActivity::class.java)
            emojiIntent.putExtra("year",year)
            emojiIntent.putExtra("month",month)
            emojiIntent.putExtra("day",day)
            emojiIntent.putExtra("content",binding.writeInputEt.text.toString())
            emojiIntent.putExtra("shareAgree",shareAgree)
            emojiIntent.putExtra("img", imgUri.toString())
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