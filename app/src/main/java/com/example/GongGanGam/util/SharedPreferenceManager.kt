package com.example.GongGanGam.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveJwt(context: Context, jwt: String) {
    val spf = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("jwt", jwt)
    editor.apply()
}

fun getJwt(context: Context) : String {
    val spf = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)

    return spf.getString("jwt", "")!!
}

fun saveUserIdx(context: Context, userIdx: Int) {
    val spf = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("userIdx", userIdx)
    editor.apply()
}

fun getUserIdx(context: Context) : Int {
    val spf = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("userIdx", 0)
}

fun saveDeviceToken(context: Context, token: String) {
    val pref = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
    pref.edit().putString("deviceToken", token).apply()
}

fun getDeviceToken(context: Context): String {
    val pref = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
    return pref.getString("deviceToken", "")!!
}