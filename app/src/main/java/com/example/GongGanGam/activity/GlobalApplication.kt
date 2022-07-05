package com.example.GongGanGam.activity

import android.app.Application
import android.content.Context
import com.example.GongGanGam.util.PrefManager
import com.example.gonggangam.R
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {

    init {
        instance = this
    }

    companion object {
        var instance: GlobalApplication? = null
        fun context(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, resources.getString(R.string.kakao_app_key))

        // Naver SDK 초기화
        NaverIdLoginSDK.initialize(this, resources.getString(R.string.naver_client_id), resources.getString(
            R.string.naver_client_secret
        ), resources.getString(R.string.naver_client_name))

        // spf 초기화
        PrefManager.init(applicationContext)
    }
}