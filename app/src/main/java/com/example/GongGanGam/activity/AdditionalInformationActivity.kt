package com.example.GongGanGam.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.GongGanGam.authService.AuthRetrofitInterface
import com.example.GongGanGam.authService.BasicResponse
import com.example.GongGanGam.authService.SignInBody
import com.example.GongGanGam.util.PrefManager
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityAdditionalInformationBinding
import com.example.GongGanGam.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdditionalInformationActivity() : AppCompatActivity() {
    lateinit var binding: ActivityAdditionalInformationBinding
    lateinit var type: String // kakao or naver
    lateinit var jwt: String // jwt
    var userIdx: Int = -1 // userIdx
    private var readyToSave : Boolean = false

    var nickName: String = ""
    var birthYear: Int = -1
    var gender: String = "N"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionalInformationBinding.inflate(layoutInflater)
        type = intent.getStringExtra("type")!! // kakao or naver
        jwt = intent.getStringExtra("jwt")!!
        userIdx = intent.getIntExtra("userIdx", -1)

        Log.d("TAG_INTENT", "type: ${type} jwt: ${jwt} userIdx: ${userIdx}")
        initListener()
        setContentView(binding.root)

    }

    private fun signIn() {
        var body = SignInBody(nickName, birthYear, gender)
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        Log.d("TAG-API-SIGNIN", body.toString())

        authService.signIn(body).enqueue(object: Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                Log.d("SIGNIN/API-RESPONSE", response.toString())

                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("SIGNIN/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> { // 회원가입 성공
                            Toast.makeText(this@AdditionalInformationActivity,"회원가입 성공",Toast.LENGTH_SHORT).show()
                            goToMainActivity()
                        }
                        else -> Toast.makeText(this@AdditionalInformationActivity,"회원가입 실패",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("SIGNIN-TAG/API-ERROR", t.message.toString())
            }

        })
    }

    private fun initListener() {
        binding.additionalGenderMaleBtn.setOnClickListener {
            if (binding.additionalGenderFemaleBtn.isSelected || binding.additionalGenderNoBtn.isSelected) {
                binding.additionalGenderMaleBtn.isSelected = true
                binding.additionalGenderFemaleBtn.isSelected = false
                binding.additionalGenderNoBtn.isSelected = false

            } else {
                binding.additionalGenderMaleBtn.isSelected =
                    binding.additionalGenderMaleBtn.isSelected != true

            }
        }
        binding.additionalGenderFemaleBtn.setOnClickListener {

            if (binding.additionalGenderMaleBtn.isSelected || binding.additionalGenderNoBtn.isSelected) {
                binding.additionalGenderFemaleBtn.isSelected = true
                binding.additionalGenderMaleBtn.isSelected = false
                binding.additionalGenderNoBtn.isSelected = false

            } else {
                binding.additionalGenderFemaleBtn.isSelected =
                    binding.additionalGenderFemaleBtn.isSelected != true
            }
        }
        binding.additionalGenderNoBtn.setOnClickListener {

            if (binding.additionalGenderMaleBtn.isSelected || binding.additionalGenderFemaleBtn.isSelected) {
                binding.additionalGenderNoBtn.isSelected = true
                binding.additionalGenderMaleBtn.isSelected = false
                binding.additionalGenderFemaleBtn.isSelected = false

            } else {
                binding.additionalGenderNoBtn.isSelected =
                    binding.additionalGenderNoBtn.isSelected != true
            }
        }
        binding.additionalBirthYearInputTv.setOnClickListener {
            callNumberPicker()
        }
        binding.additionalCompleteBtn.setOnClickListener {
            complete()
        }

        binding.additionalCl.setOnClickListener {
            hideKeyBoard()
        }
        buttonChange()
    }

    private fun callNumberPicker() {
        val dialog = AlertDialog.Builder(this).create()
        val edialog: LayoutInflater = LayoutInflater.from(this)
        val mView: View = edialog.inflate(R.layout.dialog_datepicker, null)

        val year: NumberPicker = mView.findViewById(R.id.datepicker_birth_year_picker_np)
        val save: Button = mView.findViewById(R.id.datepicker_birth_year_ok_btn)

        //  순환 안되게 막기
        year.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        //  최소값 설정
        year.minValue = 1950

        //  최대값 설정
        year.maxValue = 2022

        //초기 보여지는 값 설정
        if (binding.additionalBirthYearInputTv.text.isEmpty()) {
            year.value = 2000
        } else {
            year.value = binding.additionalBirthYearInputTv.text.toString().toInt()
        }


        //  완료 버튼 클릭 시
        save.setOnClickListener {
            binding.additionalBirthYearInputTv.text = (year.value).toString()

            dialog.dismiss()
            dialog.cancel()
        }


        dialog.setView(mView)
        dialog.create()
        dialog.show()
    }

    private fun complete() {
        if (binding.additionalNickInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.additionalBirthYearInputTv.text.toString().isEmpty()) {
            Toast.makeText(this, "출생년도를 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!binding.additionalGenderMaleBtn.isSelected && !binding.additionalGenderFemaleBtn.isSelected
            && !binding.additionalGenderNoBtn.isSelected
        ) {
            Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        nickName = binding.additionalNickInputEt.text.toString() // string
        birthYear = binding.additionalBirthYearInputTv.text.toString().toInt() // int

        if (binding.additionalGenderMaleBtn.isSelected) { // 남자
            gender = "M"
        } else if (binding.additionalGenderFemaleBtn.isSelected) { // 여자
            gender = "F"
        } else if (binding.additionalGenderNoBtn.isSelected) { // 선택하지 않음
            gender = "N"
        }

        signIn()

    }

        private fun hideKeyBoard() {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.additionalNickInputEt.windowToken, 0)
            binding.additionalNickInputEt.clearFocus()
        }

        private fun buttonChange() {
            binding.additionalNickInputEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    readyToSave = binding.additionalNickInputEt.text.toString().isNotEmpty()
//                val bir = binding.additionalBirthYearInputTv.text.toString().isNotEmpty()
//                lateinit var gen: String
//                if (binding.additionalGenderMaleBtn.isSelected) {
//                    gen = binding.additionalGenderMaleBtn.text.toString()
//                } else if (binding.additionalGenderFemaleBtn.isSelected) {
//                    gen = binding.additionalGenderFemaleBtn.text.toString()
//                } else if (binding.additionalGenderNoBtn.isSelected) {
//                    gen = binding.additionalGenderNoBtn.text.toString()
//                }
//                val genBool=gen.isNotEmpty()

                    if (readyToSave) {
                        binding.additionalCompleteBtn.setBackgroundResource(R.drawable.button_active_background)
                        binding.additionalCompleteBtn.setTextColor(resources.getColor(R.color.primaryColor))
                    } else {
                        binding.additionalCompleteBtn.setBackgroundResource(R.drawable.button_inactive_background)
                        binding.additionalCompleteBtn.setTextColor(resources.getColor(R.color.inactiveBtnColor))
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })

        }
    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        PrefManager.setAuth(jwt, userIdx)
        startActivity(intent)
        finish()
    }
}

