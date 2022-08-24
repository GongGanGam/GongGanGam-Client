package com.example.gonggangam.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object PrefManager {
    private lateinit var pref: SharedPreferences

    private const val JWT = "jwt"
    private const val USER_IDX = "userIdx"
    private const val DEVICE_TOKEN = "deviceToken"

    fun init(context: Context) {
        pref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    val jwt: String
        get() = pref.getString(JWT, "").toString()

    val userIdx: Int
        get() = pref.getInt(USER_IDX, -1)

    val deviceToken: String
        get() = pref.getString(DEVICE_TOKEN, "").toString()

    fun setAuth(jwt: String, userIdx: Int) {
        Log.d("SharePreference", "saved : $jwt $userIdx")
        pref.edit()?.apply {
            putString(JWT, jwt)
            putInt(USER_IDX, userIdx)
        }?.apply()
    }

    fun setDeviceToken(token: String) {
        pref.edit().putString(DEVICE_TOKEN, token).apply()
    }
}

//fun saveJwt(context: Context, jwt: String) {
//    val spf = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
//    val editor = spf.edit()
//
//    editor.putString("jwt", jwt)
//    editor.apply()
//}
//
//fun getJwt(context: Context) : String {
//    val spf = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
//
//    return spf.getString("jwt", "")!!
//}
//
//fun saveUserIdx(context: Context, userIdx: Int) {
//    val spf = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
//    val editor = spf.edit()
//
//    editor.putInt("userIdx", userIdx)
//    editor.apply()
//}
//
//fun getUserIdx(context: Context) : Int {
//    val spf = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
//
//    return spf.getInt("userIdx", 0)
//}
//
//fun saveDeviceToken(context: Context, token: String) {
//    val pref = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
//    pref.edit().putString("deviceToken", token).apply()
//}
//
//fun getDeviceToken(context: Context): String {
//    val pref = context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)
//    return pref.getString("deviceToken", "")!!
//}