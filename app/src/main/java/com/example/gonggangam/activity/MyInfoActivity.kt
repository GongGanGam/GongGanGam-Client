package com.example.gonggangam.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.diaryService.BasicResponse
import com.example.gonggangam.myPageService.MyPageRetrofitInterface
import com.example.gonggangam.util.PrefManager
import com.example.gonggangam.util.getRetrofit
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityMyInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyInfoBinding
    var nickname: String? = ""
    var birthYear: Int = -1
    var setAge: String = ""
    var gender: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initInfo()
        initListener()
    }

    private fun initInfo() {
        nickname = intent.getStringExtra("nickname")
        birthYear = intent.getIntExtra("birthYear", -1)

        if(nickname == null || birthYear == -1) {
            Toast.makeText(this@MyInfoActivity, "회원 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }

        else {
            binding.myInfoNickInputEt.setText(nickname)
            binding.myInfoBirthYearInputTv.text = birthYear.toString()
        }

        //MyInfo 에 맞는 헤더 정보 수정
        binding.myInfoLayoutHeader.layoutHeaderTitleTv.text = "내 정보"
        binding.myInfoLayoutHeader.layoutHeaderBtnTv.text = "저장"

        //임의로 버튼 선택 -> 추후 삭제해야함!
        binding.myInfoAgeNoBtn.isSelected = true
        when {
            binding.myInfoAgeNoBtn.isSelected -> {
                setAge = "F"
            }
            binding.myInfoAgeSimilarBtn.isSelected -> {
                setAge = "T"
            }
        }

        binding.myInfoGenderNoBtn.isSelected = true
        when {
            binding.myInfoGenderNoBtn.isSelected -> {
                gender = "N"
            }
            binding.myInfoGenderMaleBtn.isSelected -> {
                gender = "M"
            }
            binding.myInfoGenderFemaleBtn.isSelected -> {
                gender = "F"
            }
        }

    }

    private fun initListener() {
        binding.myInfoLayoutHeader.layoutHeaderBackIv.setOnClickListener {
            finish()
        }

        binding.myInfoCl.setOnClickListener {
            hideKeyBoard()
        }

        //연령대 지정 버튼
        binding.myInfoAgeSimilarBtn.setOnClickListener {
            // setAge = "T"
            if(binding.myInfoAgeNoBtn.isSelected ){
                binding.myInfoAgeSimilarBtn.isSelected = true
                binding.myInfoAgeNoBtn.isSelected = false
            }
            else{
                binding.myInfoAgeSimilarBtn.isSelected =  binding.myInfoAgeSimilarBtn.isSelected != true
            }
        }
        binding.myInfoAgeNoBtn.setOnClickListener {
            // setAge = "F"
            if(binding.myInfoAgeSimilarBtn.isSelected ){
                binding.myInfoAgeNoBtn.isSelected = true
                binding.myInfoAgeSimilarBtn.isSelected = false

            }
            else{
                binding.myInfoAgeNoBtn.isSelected =  binding.myInfoAgeNoBtn.isSelected != true
            }
        }
        //성별 선택 버튼
        binding.myInfoGenderMaleBtn.setOnClickListener {
            // gender = "M"
            if(binding.myInfoGenderFemaleBtn.isSelected||binding.myInfoGenderNoBtn.isSelected){
                binding.myInfoGenderMaleBtn.isSelected = true
                binding.myInfoGenderFemaleBtn.isSelected = false
                binding.myInfoGenderNoBtn.isSelected = false

            }
            else{
                binding.myInfoGenderMaleBtn.isSelected =  binding.myInfoGenderMaleBtn.isSelected != true
            }
        }
        binding.myInfoGenderFemaleBtn.setOnClickListener {
            // gender = "F"
            if(binding.myInfoGenderMaleBtn.isSelected ||binding.myInfoGenderNoBtn.isSelected ){
                binding.myInfoGenderFemaleBtn.isSelected = true
                binding.myInfoGenderMaleBtn.isSelected = false
                binding.myInfoGenderNoBtn.isSelected = false

            }
            else{
                binding.myInfoGenderFemaleBtn.isSelected =  binding.myInfoGenderFemaleBtn.isSelected != true
            }
        }
        binding.myInfoGenderNoBtn.setOnClickListener {
            // gender = "N"
            if(binding.myInfoGenderMaleBtn.isSelected ||binding.myInfoGenderFemaleBtn.isSelected ){
                binding.myInfoGenderNoBtn.isSelected = true
                binding.myInfoGenderMaleBtn.isSelected = false
                binding.myInfoGenderFemaleBtn.isSelected = false

            }
            else{
                binding.myInfoGenderNoBtn.isSelected =  binding.myInfoGenderNoBtn.isSelected != true
            }
        }

        //출생년도 설정
        binding.myInfoBirthYearInputTv.setOnClickListener {
            callNumberPicker()
        }

        //정보를 서버에 넘김
        binding.myInfoLayoutHeader.layoutHeaderBtnTv.setOnClickListener {
            complete()
        }

        binding.myInfoLeaveBtnTv.setOnClickListener {
            startActivity(Intent(this, MyPageLeaveActivity::class.java))
        }
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

        year.value = binding.myInfoBirthYearInputTv.text.toString().toInt()


        //  완료 버튼 클릭 시
        save.setOnClickListener {
            binding.myInfoBirthYearInputTv.text = (year.value).toString()
            // birthYear = (year.value)
            dialog.dismiss()
            dialog.cancel()
        }


        dialog.setView(mView)
        dialog.create()
        dialog.show()
    }

    private fun complete() {
        if (binding.myInfoNickInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.myInfoBirthYearInputTv.text.toString().isEmpty()) {
            Toast.makeText(this, "출생년도를 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!binding.myInfoAgeSimilarBtn.isSelected && !binding.myInfoAgeNoBtn.isSelected) {
            Toast.makeText(this, "연령대 지정 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!binding.myInfoGenderMaleBtn.isSelected && !binding.myInfoGenderFemaleBtn.isSelected
            && !binding.myInfoGenderNoBtn.isSelected
        ) {
            Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        nickname = binding.myInfoNickInputEt.text.toString()
        birthYear = binding.myInfoBirthYearInputTv.text.toString().toInt()

        //성별
        when {
            binding.myInfoGenderMaleBtn.isSelected -> {
                gender = "M"
            }
            binding.myInfoGenderFemaleBtn.isSelected -> {
                gender = "F"
            }
            binding.myInfoGenderNoBtn.isSelected -> {
                gender = "N"
            }
        }

        //나이
        if (binding.myInfoAgeSimilarBtn.isSelected) {
            setAge = "T"
        } else if (binding.myInfoAgeNoBtn.isSelected) {
            setAge = "F"
        }

        val myPageService = getRetrofit().create(MyPageRetrofitInterface::class.java)
        myPageService.editUserInfo(nickname!!, birthYear, setAge, gender, PrefManager.userIdx)
            .enqueue(object: Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful && response.code() == 200) {
                        val resp = response.body()!!
                        Log.d("TAG/API-RESPONSE", resp.toString())

                        when(resp.code) {
                            1000 -> Toast.makeText(this@MyInfoActivity, "회원 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show()
                            else -> Log.d("TAG/API-CODE", "정보 수정 실패" )
                        }
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("TAG/API-FAIL", "호출 실패")
                }

            })

    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.myInfoNickInputEt.windowToken, 0)
        binding.myInfoNickInputEt.clearFocus()
    }
}
