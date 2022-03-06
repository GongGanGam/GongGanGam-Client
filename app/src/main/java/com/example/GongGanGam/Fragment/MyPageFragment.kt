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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.gonggangam.Activity.MyPageNoticeActivity
import java.io.File


class MyPageFragment() : Fragment() {
    private var clicked = false
    private val PHOTO_NAME = "photo"
    private lateinit var photoFile : File

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)

        binding.mypageCsNoticeTv.setOnClickListener {
            startActivity(Intent(activity, MyPageNoticeActivity::class.java))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
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
    }

    private fun chooseImageFromGallery() {
        selectedImages.launch("image/*")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}