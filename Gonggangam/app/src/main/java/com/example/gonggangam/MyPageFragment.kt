package com.example.GongGanGam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.GongGanGam.databinding.FragmentMyPageBinding
import com.example.gonggangam.MyInfoActivity

class MyPageFragment() : Fragment() {
    lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        //프로필 사진 넣기
//        mypage_user_add_profile.setOnClickListener{
//
//        }

        binding.mypageCsNoticeTv.setOnClickListener {
            startActivity(Intent(activity, MyPageNoticeActivity::class.java))
        }

        binding.mypageMypageTv.setOnClickListener {
            startActivity(Intent(activity, DiaryWriteEmojiActivity::class.java))
        }

        binding.mypageEditBtn.setOnClickListener {
            //내 정보 수정 부분으로 넘어가게끔 수정했습니다
            startActivity(Intent(activity, MyInfoActivity::class.java))
        }

        return binding.root
    }
}