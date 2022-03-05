package com.example.gonggangam

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this,"f2b1ab86a36bc9f3c9717a5799a8fbf8")
    }
}