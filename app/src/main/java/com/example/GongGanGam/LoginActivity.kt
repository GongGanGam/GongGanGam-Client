package com.example.gonggangam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    var kakaoAccessToken : String = ""
    var kakaoId: String = ""
    var kakaoEmail: String = ""
    var loginSuccess: Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListener()
    }

    fun initListener() {
        binding.loginKakaoBtn.setOnClickListener {
//            CoroutineScope(Dispatchers.Main).launch {
//                launch {
//                   onKakao()
//                }.join()
//
//                launch {
//                    goToAdditionalInfo(kakaoAccessToken, kakaoId, kakaoAccessToken)
//                }
//            }
            onKakao()
            login("code", "18d1c44e27c5b6b64c2e4a2e58a90361", "http://3.34.199.201:3000/app/users/login/kakao")
        }

        binding.loginNaverBtn.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.d("TAG_KAKAO", "연결 끊기 실패", error)
                }
                else {
                    Log.d("TAG_KAKAO", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }
        }
    }

    fun login(response_type: String, client_id: String, redirect_uri: String) {
        val authService = getKakaoLoginRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(response_type, client_id, redirect_uri).enqueue(object:
            Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("KAKAO/API-RESPONSE", response.toString())

                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("KAKAO/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> loginSuccess = true
                        else -> loginSuccess = false
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/API-ERROR", t.message.toString())
            }

        })
    }
    fun onKakao(){
        // 카카오 서버 응답에 대한 callback
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("TAG_KAKAO", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.d("TAG_KAKAO", "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡 앱이 존재하면 열기
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.d("TAG_KAKAO", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, prompts = listOf(Prompt.LOGIN), callback = callback)
                } else if (token != null) {
                    Log.d("TAG_KAKAO", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    kakaoAccessToken = token.accessToken
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
        getUserProfile()
    }

    fun getUserProfile() {
        UserApiClient.instance.me { user, error ->
            Log.d("TAG_KAKAO", "유저 정보 요청 중")
            if (error != null) {
                Log.d("TAG_KAKAO", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.d("TAG_KAKAO", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}")
                kakaoId= user.id.toString()
                kakaoEmail= user.kakaoAccount?.email.toString()
            }
        }
    }

    fun goToAdditionalInfo(token: String, id: String, email:String) {
        val intent = Intent(this, AdditionalInformationActivity::class.java)
        intent.putExtra("token", token)
        intent.putExtra("id", id)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }

}
