package com.example.gonggangam

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, resources.getString(R.string.kakao_app_key))

        // Naver SDK 초기화
        NaverIdLoginSDK.initialize(this, resources.getString(R.string.naver_client_id), resources.getString(R.string.naver_client_secret), resources.getString(R.string.naver_client_name))
    }
}