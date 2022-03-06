package com.example.gonggangam.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.AuthService.AuthResponse
import com.example.gonggangam.AuthService.AuthRetrofitInterface
import com.example.gonggangam.databinding.ActivityLoginBinding
import com.example.gonggangam.getKakaoLoginRetrofit
import com.example.gonggangam.getNaverLoginRetrofit
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    var kakaoAccessToken : String = ""
    var kakaoId: String = ""
    var kakaoEmail: String = ""
    var naverAccessToken: String = ""
    var naverId: String = ""
    var naverEmail: String = ""
    var loginSuccess: Boolean = false
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
            kakaoLogin("code", "18d1c44e27c5b6b64c2e4a2e58a90361", "http://3.34.199.201:3000/app/users/login/kakao")
        }

        binding.loginNaverBtn.setOnClickListener {
            onNaver()
            naverLogin("code","uQjTzvqHGDi0sdby9nIL","http://localhost:3000/app/users/login/naver&state=1111" )
        }
    }

    fun onNaver() {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverAccessToken = NaverIdLoginSDK.getAccessToken().toString()
                Log.d("TAG/NAVER", naverAccessToken)
                getUserProfile("naver")
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@LoginActivity,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    fun naverLogin(response_type: String, client_id: String, redirect_uri: String) {
        val authService = getNaverLoginRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(response_type, client_id, redirect_uri).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("NAVER/API-RESPONSE", response.toString())

                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("NAVER/API-RESPONSE", resp.toString())

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
    fun kakaoLogin(response_type: String, client_id: String, redirect_uri: String) {
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
        getUserProfile("kakao")
    }

    fun getUserProfile(type: String) {
        if(type == "kakao") {
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
        }}
        else if(type == "naver") {
            NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                override fun onSuccess(response: NidProfileResponse) {
                    Log.d("TAG_NAVER", response.toString())
                }
                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    Toast.makeText(this@LoginActivity, "errorCode: $errorCode, errorDesc: $errorDescription", Toast.LENGTH_SHORT).show()
                }
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            })
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
