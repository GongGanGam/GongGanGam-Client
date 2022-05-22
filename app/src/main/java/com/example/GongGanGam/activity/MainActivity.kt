package com.example.GongGanGam.activity

import com.example.GongGanGam.fragment.MyPageFragment
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.*
import com.example.GongGanGam.fragment.ChatFragment
import com.example.GongGanGam.fragment.DiaryFragment
import com.example.GongGanGam.fragment.LetterFragment
import com.example.GongGanGam.util.getJwt
import com.example.GongGanGam.util.getUserIdx
import com.example.gonggangam.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var jwt: String = ""
    var userIdx: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BottomNaviTemplate)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainBtmNav.itemIconTintList = null

        Log.d("TAG-MAIN", "jwt: ${getJwt(this)} userIdx: ${getUserIdx(this)}")

        // dummy
//        if(jwt == "" || userIdx < 0) {
//            Toast.makeText(this, "유저 정보 확인 실패", Toast.LENGTH_SHORT).show()
//            saveJwt(this, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWR4Ijo4LCJpYXQiOjE2NDM4ODI0MjcsImV4cCI6MTY3NTQxODQyNywic3ViIjoidXNlckluZm8ifQ.z5I8Vuv6kNK4ILB-s9mQSQvii6w5FmWJtaFq-AtZ_zQ")
//            saveUserIdx(this, 8)
//        }

        // 회원 정보 저장
//        saveJwt(this, jwt)
//        saveUserIdx(this, userIdx)
        // init clear status bar
//        val window = window
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//
//        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
//            WindowCompat.setDecorFitsSystemWindows(window, false)
//        }

        // init bottom navigation
        initNavigation()
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, DiaryFragment())
            .commitAllowingStateLoss()

        binding.mainBtmNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.diaryFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, DiaryFragment())
                        .commitAllowingStateLoss()
                    binding.mainBtmNav.setBackgroundResource(R.drawable.btm_background_diary)
                    return@setOnItemSelectedListener true
                }

                R.id.letterFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LetterFragment())
                        .commitAllowingStateLoss()

                    binding.mainBtmNav.setBackgroundResource(R.drawable.background_solid)
                    return@setOnItemSelectedListener true
                }

                R.id.chatFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ChatFragment())
                        .commitAllowingStateLoss()

                    binding.mainBtmNav.setBackgroundResource(R.drawable.background_solid)
                    return@setOnItemSelectedListener true
                }

                R.id.mypageFragment -> {
                    setFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MyPageFragment())
                        .commitAllowingStateLoss()

                    binding.mainBtmNav.setBackgroundResource(R.drawable.background_solid)
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }

    private fun setFragment() {
        val MyPageFragment = MyPageFragment()
        supportFragmentManager
            .beginTransaction()
            .add(binding.mainFrm.id, MyPageFragment, "PICK_IMAGE_FRAGMENT")
            .commit()
    }




}