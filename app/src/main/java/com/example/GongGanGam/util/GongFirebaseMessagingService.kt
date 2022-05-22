package com.example.GongGanGam.util

import android.util.Log
import com.example.GongGanGam.activity.GlobalApplication
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class GongFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d(TAG, "New FCM device token : $token")
        saveDeviceToken(GlobalApplication.context(), token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // TODO : 메시지 수신 후 처리
    }

    companion object {
        private const val TAG = "FCM Service"
    }
}