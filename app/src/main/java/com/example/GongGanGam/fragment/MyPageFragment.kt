package com.example.GongGanGam.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.GongGanGam.activity.DiaryWriteActivity
import com.example.GongGanGam.activity.LoginActivity
import com.example.GongGanGam.activity.MyInfoActivity
import com.example.GongGanGam.activity.MyPageNoticeActivity
import com.example.GongGanGam.diaryService.BasicResponse
import com.example.GongGanGam.myPageService.MyPageRetrofitInterface
import com.example.GongGanGam.myPageService.UserResponse
import com.example.GongGanGam.util.FormDataUtil
import com.example.GongGanGam.util.getJwt
import com.example.GongGanGam.util.getRetrofit
import com.example.GongGanGam.util.getUserIdx
import com.example.gonggangam.databinding.FragmentMyPageBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MyPageFragment() : Fragment() {

    lateinit var diaryImg:String // diaryImg uri 저장 변수
    lateinit var observer: DiaryWriteActivity.MyLifecycleObserver

    private var clicked = false
    private val PHOTO_NAME = "photo"
    private lateinit var photoFile : File
    lateinit var binding: FragmentMyPageBinding

    private val selectedImages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {

            // 이미지 uri 넣기
            diaryImg = result.data?.data.toString() // image uri 저장
            Log.d("TAG_WRITE_RESULT", diaryImg)

            // Glide로 띄우기, 모서리 조정
            Glide.with(this).load(result.data?.data)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.mypageUserProfileNone)

            // val realPath = FormDataUtil.getRealPathFromURI(result.data?.data!!, requireContext())!!
            // val destFile = File(realPath)
            // val multiBody = FormDataUtil.getImageBody("image", destFile)
            // val bmp = MultipartBody.Part.createFormData("IMG_FILE", destFile.name)

//            val file = File(Environment.getExternalStorageDirectory(), "/path/")
//            if (!file.exists())
//                file.mkdir()
//
//            val imageName = "fileName.jpeg"
//            val imageFile = File("${Environment.getExternalStorageDirectory().absoluteFile}/path/",
//                imageName
//            )
//            val imagePath = imageFile.absolutePath

            val imgUri = result.data?.data!!
            val file = File(imgUri.toString())
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val requestBody = MultipartBody.Part.createFormData("profImg", file.name, requestFile)

//            val imgPath = FormDataUtil.getPath(requireContext(), imgUri)
//            if(imgPath != null) {
//                val inputStream: InputStream =
//                    context?.contentResolver?.openInputStream(imgUri)!! //src
//                val extension = imgPath.substring(imgPath.lastIndexOf("."))
//                val localImgFile = File(requireContext().filesDir, "localImgIFile$extension")
//
//                inputStream.use { inputStream ->
//                    val out = FileOutputStream(localImgFile)
//                    out.use { out ->
//                        // Transfer bytes from in to out
//                        val buf = ByteArray(1024)
//                        var len:Int
//                        while ((inputStream.read(buf).also { len = it }) > 0) {
//                            out.write(buf, 0, len);
//                        }
//                    }
//                }
//                val requestBody = localImgFile.asRequestBody("image/jpg".toMediaTypeOrNull())
//                val profImg = MultipartBody.Part.createFormData("profImg", localImgFile.name, requestBody)
//
//            }

//            val realPath = FormDataUtil.getRealPathFromURI(imgUri, requireContext())
//            val file = File(realPath)
//            if(!file.exists()) {
//                file.mkdirs()
//            }
//            val requestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
//            val profImg = MultipartBody.Part.createFormData("profImg", file.name, requestBody)
            val myPageService = getRetrofit().create(MyPageRetrofitInterface::class.java)
                myPageService.editProfileImage(getJwt(requireContext()), requestBody, getUserIdx(requireContext())).enqueue(object: Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if(response.isSuccessful && response.code() == 200) {
                            val resp = response.body()!!
                            Log.d("TAG/API-RESPONSE", resp.toString())

                            when(resp.code) {
                                1000 -> Log.d("TAG/API-CODE", "프로필 업로드 완료" )
                                else -> Log.d("TAG/API-CODE", "프로필 업로드 실패" )
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("TAG/API-ERROR", t.message.toString())
                    }

                })


        }

        else {
            Log.d("TAG_WRITE_ERROR", "이미지 불러오기 취소")
        }
    }

    private val galleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            chooseImageFromGallery()
        }
        else {
            Toast.makeText(context,"갤러리 접근을 허용합니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        binding.mypageCsNoticeTv.setOnClickListener {
            startActivity(Intent(activity, MyPageNoticeActivity::class.java))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        getUser()
    }

    private fun getUser() {
        val authService = getRetrofit().create(MyPageRetrofitInterface::class.java)
        authService.getUser(getJwt(requireContext()), getUserIdx(requireContext())).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("TAG-USER", response.toString())

                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG-USER", resp.toString())

                    when(resp.code) {
                        1000 -> {
                            binding.mypageUserNameTv.text = resp.result!!.nickname
                            binding.mypageUserBirthyearTv.text = resp.result!!.birthYear

                            // 연령대 & 성별

                            // toggle
                        }
                        else -> Toast.makeText(requireContext(),"fail get user",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("TAG-USER", t.message.toString())
            }

        })
    }

    private fun initClickListeners() {
        binding.mypageUserAddProfile.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        binding.mypageEditBtn.setOnClickListener {
            val intent = Intent(requireContext(), MyInfoActivity::class.java)
            startActivity(intent)
        }

        binding.mypageLogoutTv.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        // editor.remove("jwt")
        editor.remove("userIdx")
        editor.remove("jwt")
        editor.apply()
        startActivity(intent)
    }

    private fun chooseImageFromGallery() {
        // selectedImages.launch("image/*")
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        selectedImages.launch(intent)
    }

    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }


}