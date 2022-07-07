package com.example.gonggangam.activity

import com.example.gonggangam.fragment.MyPageFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.*
import com.example.gonggangam.fragment.ChatFragment
import com.example.gonggangam.fragment.DiaryFragment
import com.example.gonggangam.fragment.LetterFragment
import com.example.gonggangam.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BottomNaviTemplate)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainBtmNav.itemIconTintList = null

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