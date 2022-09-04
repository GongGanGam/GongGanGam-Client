package com.example.gonggangam.util

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.activity.LoginActivity

object AuthUtil {

    fun logout(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        editor.remove("userIdx")
        editor.remove("jwt")
        editor.apply()
        activity.startActivity(intent)
    }
}