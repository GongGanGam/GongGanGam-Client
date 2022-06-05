package com.example.GongGanGam.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import com.example.GongGanGam.activity.LoginActivity
import com.example.GongGanGam.activity.MyInfoActivity
import com.example.GongGanGam.activity.MyPageNoticeActivity
import com.example.GongGanGam.diaryService.BasicResponse
import com.example.GongGanGam.myPageService.MyPageRetrofitInterface
import com.example.GongGanGam.myPageService.UserResponse
import com.example.GongGanGam.util.FormDataUtil
import com.example.GongGanGam.util.PrefManager
import com.example.GongGanGam.util.getRetrofit
import com.example.gonggangam.databinding.FragmentMyPageBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class MyPageFragment : Fragment() {

    private lateinit var imgUri: Uri // diaryImg uri 저장 변수
    lateinit var binding: FragmentMyPageBinding

    private val selectedImages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {

            // 이미지 uri 넣기
            imgUri = result.data?.data!! // image uri 저장
            Log.d("TAG_WRITE_RESULT", imgUri.toString())

            Glide.with(this).load(imgUri)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(binding.mypageUserProfileNone)

            // retrofit 통신
            uploadProfImg()
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
        authService.getUser(PrefManager.userIdx).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.d("TAG-USER", response.toString())

                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG-USER", resp.toString())

                    when(resp.code) {
                        1000 -> {
                            binding.mypageUserNameTv.text = resp.result!!.nickname
                            binding.mypageUserBirthyearTv.text = resp.result.birthYear
                            activity?.let {
                                Glide.with(it).load(resp.result.profImg)
                                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                                    .into(binding.mypageUserProfileNone)
                            }

                            // 연령대 & 성별

                            // toggle
                            binding.mypageAcceptDiaryToggleSw.isChecked = resp.result.diaryPush == "T"
                            binding.mypageAcceptReplyToggleSw.isChecked = resp.result.answerPush == "T"
                            binding.mypageChattingToggleSw.isChecked = resp.result.chatPush == "T"
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

    private fun uploadProfImg() {
        // 이미지 절대경로로 파일 변환
        val realPath = FormDataUtil.getRealPathFromURI(imgUri, requireContext())
        val destFile = File(realPath)
        if(!destFile.exists())
            destFile.mkdirs()
        val requestFile = destFile.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Part.createFormData("profImg", destFile.name, requestFile)

        val myPageService = getRetrofit().create(MyPageRetrofitInterface::class.java)
        myPageService.editProfileImage(requestBody, PrefManager.userIdx).enqueue(object: Callback<BasicResponse>{
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

    private fun initClickListeners() {
        binding.mypageUserProfileNone.setOnClickListener {
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

        binding.mypageAcceptDiaryToggleSw.setOnClickListener {
            val isPush: String = if(binding.mypageAcceptDiaryToggleSw.isChecked) {
                "T"
            } else {
                "F"
            }
            setPushAlarm(PushType.DIARY, isPush)
        }

        binding.mypageAcceptReplyToggleSw.setOnClickListener {
            val isPush: String = if(binding.mypageAcceptReplyToggleSw.isChecked) {
                "T"
            } else {
                "F"
            }
            setPushAlarm(PushType.ANSWER, isPush)
        }

        binding.mypageChattingToggleSw.setOnClickListener {
            val isPush: String = if(binding.mypageChattingToggleSw.isChecked) {
                "T"
            } else {
                "F"
            }
            setPushAlarm(PushType.CHAT, isPush)
        }
    }

    private fun setPushAlarm(type: PushType, isPush: String) {
        val myPageService = getRetrofit().create(MyPageRetrofitInterface::class.java)
        when(type) {
            PushType.DIARY -> {
                myPageService.setDiaryPush(PrefManager.userIdx, isPush).enqueue(object: Callback<BasicResponse>{

                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if(response.isSuccessful && response.code() == 200) {
                            val resp = response.body()!!
                            Log.d("TAG/API-RESPONSE", resp.toString())

                            when(resp.code) {
                                1000 -> Log.d("TAG/API-CODE", "다이어리 알람 설정 성공" )
                                else -> Log.d("TAG/API-CODE", "다이어리 알람 설정 실패" )
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("TAG/API_FAIL", "API 호출 실패")
                    }

                })
            }

            PushType.ANSWER -> {
                myPageService.setAnswerPush(PrefManager.userIdx, isPush).enqueue(object: Callback<BasicResponse>{

                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if(response.isSuccessful && response.code() == 200) {
                            val resp = response.body()!!
                            Log.d("TAG/API-RESPONSE", resp.toString())

                            when(resp.code) {
                                1000 -> Log.d("TAG/API-CODE", "답장 알람 설정 성공" )
                                else -> Log.d("TAG/API-CODE", "답장 알람 설정 실패" )
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("TAG/API_FAIL", "API 호출 실패")
                    }

                })
            }

            PushType.CHAT -> {
                myPageService.setChatPush(PrefManager.userIdx, isPush).enqueue(object: Callback<BasicResponse>{

                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if(response.isSuccessful && response.code() == 200) {
                            val resp = response.body()!!
                            Log.d("TAG/API-RESPONSE", resp.toString())

                            when(resp.code) {
                                1000 -> Log.d("TAG/API-CODE", "채팅 알람 설정 성공" )
                                else -> Log.d("TAG/API-CODE", "채팅 알람 설정 실패" )
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("TAG/API_FAIL", "API 호출 실패")
                    }

                })
            }
        }

    }

    private fun logout() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        editor.remove("userIdx")
        editor.remove("jwt")
        editor.apply()
        startActivity(intent)
    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        selectedImages.launch(intent)
    }

    enum class PushType {
        DIARY,
        ANSWER,
        CHAT
    }
}
