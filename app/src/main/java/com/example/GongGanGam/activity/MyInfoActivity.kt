package com.example.gonggangam.Activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityMyInfoBinding

class MyInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //MyInfo 에 맞는 헤더 정보 수정
        binding.myInfoLayoutHeader.layoutHeaderTitleTv.text = "내 정보"
        binding.myInfoLayoutHeader.layoutHeaderBtnTv.text = "저장"

        binding.myInfoLayoutHeader.layoutHeaderBackIv.setOnClickListener {
           finish()
        }

        binding.myInfoCl.setOnClickListener {
            hideKeyBoard()
        }

        //임의로 버튼 선택 -> 추후 삭제해야함!
        binding.myInfoGenderNoBtn.isSelected = true
        binding.myInfoAgeNoBtn.isSelected = true
        //연령대 지정 버튼
        binding.myInfoAgeSimilarBtn.setOnClickListener {
            if(binding.myInfoAgeNoBtn.isSelected ){
                binding.myInfoAgeSimilarBtn.isSelected = true
                binding.myInfoAgeNoBtn.isSelected = false

            }
            else{
                binding.myInfoAgeSimilarBtn.isSelected =  binding.myInfoAgeSimilarBtn.isSelected != true

            }
        }
        binding.myInfoAgeNoBtn.setOnClickListener {

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
            if(binding.myInfoGenderFemaleBtn.isSelected||binding.myInfoGenderNoBtn.isSelected ){
                binding.myInfoGenderMaleBtn.isSelected = true
                binding.myInfoGenderFemaleBtn.isSelected = false
                binding.myInfoGenderNoBtn.isSelected = false

            }
            else{
                binding.myInfoGenderMaleBtn.isSelected =  binding.myInfoGenderMaleBtn.isSelected != true

            }
        }
        binding.myInfoGenderFemaleBtn.setOnClickListener {

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

        val nickName: String = binding.myInfoNickInputEt.text.toString()
        val birthYear: String = binding.myInfoBirthYearInputTv.text.toString()
        lateinit var gender: String
        lateinit var age: String

        //성별
        if (binding.myInfoGenderMaleBtn.isSelected) {
            gender = binding.myInfoGenderMaleBtn.text.toString()
        } else if (binding.myInfoGenderFemaleBtn.isSelected) {
            gender = binding.myInfoGenderFemaleBtn.text.toString()
        } else if (binding.myInfoGenderNoBtn.isSelected) {
            gender = binding.myInfoGenderNoBtn.text.toString()
        }

        //나이
        if (binding.myInfoAgeSimilarBtn.isSelected) {
            age = binding.myInfoAgeSimilarBtn.text.toString()
        } else if (binding.myInfoAgeNoBtn.isSelected) {
            age = binding.myInfoAgeNoBtn.text.toString()
        }

        Toast.makeText(this, "nickname $nickName birthYear $birthYear gender $gender age $age", Toast.LENGTH_SHORT).show()


    }
    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.myInfoNickInputEt.windowToken, 0)
        binding.myInfoNickInputEt.clearFocus()
    }
}
