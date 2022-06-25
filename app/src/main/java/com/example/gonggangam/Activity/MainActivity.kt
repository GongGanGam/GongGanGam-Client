package com.example.gonggangam.Activity

import android.content.Intent
import com.example.gonggangam.Fragment.MyPageFragment
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.*
import com.example.gonggangam.Fragment.ChatFragment
import com.example.gonggangam.Fragment.DiaryFragment
import com.example.gonggangam.Fragment.LetterFragment
import com.example.gonggangam.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var jwt: String = ""
    var userIdx: Int = -1
    var thisYear: Int = 0
    var thisMonth: Int = 0
    var thisDay: Int = 0

//     fun onDatePass(year: Int, month: Int, day: Int) {
//        thisYear=year
//        thisMonth=month
//        thisDay = day
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BottomNaviTemplate)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainBtmNav.itemIconTintList = null

        Log.d("TAG-MAIN", "jwt: ${getJwt(this)} userIdx: ${getUserIdx(this)}")



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
    fun receiveData(year: Int, month: Int, day: Int) {
        thisYear=year
        thisMonth=month
        thisDay = day
        val intent = Intent(this, DiaryWriteEmojiActivity::class.java)
        intent.putExtra("year",year)
        intent.putExtra("month",month)
        intent.putExtra("day",day)


        startActivity(intent)
    }





}