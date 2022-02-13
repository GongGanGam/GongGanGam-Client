package com.example.gonggangam

import MyPageFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.DiaryFragment
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BottomNaviTemplate)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainBtmNav.itemIconTintList = null

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