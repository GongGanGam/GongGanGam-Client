package com.example.gonggangam.Fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gonggangam.*
import com.example.gonggangam.databinding.FragmentMyPageBinding
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.gonggangam.Activity.LoginActivity
import com.example.gonggangam.Activity.MyInfoActivity
import com.example.gonggangam.Activity.MyPageNoticeActivity
import com.example.gonggangam.MyPageService.MyPageRetrofitInterface
import com.example.gonggangam.MyPageService.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class MyPageFragment() : Fragment() {
    private var clicked = false
    private val PHOTO_NAME = "photo"
    private lateinit var photoFile : File
    lateinit var binding: FragmentMyPageBinding

    private val selectedImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
        binding.mypageUserProfileNone.setImageURI(uriList[0])
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
        selectedImages.launch("image/*")
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }



}