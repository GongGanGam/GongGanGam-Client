package com.example.GongGanGam

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.GongGanGam.DiaryWriteEmojiActivity
import com.example.GongGanGam.databinding.FragmentMyPageBinding

class MyPageFragment() : Fragment() {
    lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        binding.mypageCsNoticeTv.setOnClickListener {
            startActivity(Intent(activity, MyPageNoticeActivity::class.java))
        }

        binding.mypageMypageTv.setOnClickListener {
            startActivity(Intent(activity, DiaryWriteEmojiActivity::class.java))
        }

        return binding.root
    }
}