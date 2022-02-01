package com.example.gonggangam

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.gonggangam.databinding.ActivityAdditionalInformationBinding
import com.example.gonggangam.databinding.DialogDatepickerBinding

class AdditionalInformationActivity() : AppCompatActivity() {
    lateinit var binding : ActivityAdditionalInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionalInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.additionalSexMaleBtn.setOnClickListener {
            if(binding.additionalSexFemaleBtn.isSelected||binding.additionalSexNoBtn.isSelected ){
                binding.additionalSexMaleBtn.isSelected = true
                binding.additionalSexFemaleBtn.isSelected = false
                binding.additionalSexNoBtn.isSelected = false

            }
            else{
                binding.additionalSexMaleBtn.isSelected =  binding.additionalSexMaleBtn.isSelected != true

            }
        }
        binding.additionalSexFemaleBtn.setOnClickListener {

            if(binding.additionalSexMaleBtn.isSelected ||binding.additionalSexNoBtn.isSelected ){
                binding.additionalSexFemaleBtn.isSelected = true
                binding.additionalSexMaleBtn.isSelected = false
                binding.additionalSexNoBtn.isSelected = false

            }
            else{
                binding.additionalSexFemaleBtn.isSelected =  binding.additionalSexFemaleBtn.isSelected != true
            }
        }
        binding.additionalSexNoBtn.setOnClickListener {

            if(binding.additionalSexMaleBtn.isSelected ||binding.additionalSexFemaleBtn.isSelected ){
                binding.additionalSexNoBtn.isSelected = true
                binding.additionalSexMaleBtn.isSelected = false
                binding.additionalSexFemaleBtn.isSelected = false

            }
            else{
                binding.additionalSexNoBtn.isSelected =  binding.additionalSexNoBtn.isSelected != true
            }
        }
        binding.additionalBirthYearInputTv.setOnClickListener {
            callNumberPicker()
        }
        binding.additionalCompleteBtn.setOnClickListener{
            complete()
        }
    }
    private fun callNumberPicker(){
        val dialog = AlertDialog.Builder(this).create()
        val edialog : LayoutInflater = LayoutInflater.from(this)
        val mView : View = edialog.inflate(R.layout.dialog_datepicker,null)

        val year : NumberPicker = mView.findViewById(R.id.datepicker_birth_year_picker_np)
        val save : Button = mView.findViewById(R.id.datepicker_birth_year_ok_btn)

        //  순환 안되게 막기
        year.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        //  최소값 설정
        year.minValue = 1950

        //  최대값 설정
        year.maxValue = 2022

        //초기 보여지는 값 설정
        if(binding.additionalBirthYearInputTv.text.isEmpty()) {
            year.value = 2000
        }
        else{
            year.value = binding.additionalBirthYearInputTv.text.toString().toInt()
        }



        //  완료 버튼 클릭 시
        save.setOnClickListener {
            binding.additionalBirthYearInputTv.text=(year.value).toString()

            dialog.dismiss()
            dialog.cancel()
        }


        dialog.setView(mView)
        dialog.create()
        dialog.show()
    }
    private fun complete(){
        if (binding.additionalNickInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.additionalBirthYearInputTv.text.toString().isEmpty()) {
            Toast.makeText(this, "출생년도를 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (!binding.additionalSexMaleBtn.isSelected&&!binding.additionalSexFemaleBtn.isSelected
            &&!binding.additionalSexNoBtn.isSelected) {
            Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        val nickName: String = binding.additionalNickInputEt.text.toString()
        val birthYear: String = binding.additionalBirthYearInputTv.text.toString()
        lateinit var sex : String

        if(binding.additionalSexMaleBtn.isSelected){
            sex = binding.additionalSexMaleBtn.text.toString()
        }

        else if(binding.additionalSexFemaleBtn.isSelected){
            sex = binding.additionalSexFemaleBtn.text.toString()
        }

        else if(binding.additionalSexNoBtn.isSelected){
            sex = binding.additionalSexNoBtn.text.toString()
        }

       Toast.makeText(this, "nickname $nickName birthYear $birthYear sex $sex", Toast.LENGTH_SHORT).show()


//
//        user?.let {
//            Log.d("LOGINACT/GET_USER", "userId: ${user.id}, $user")
//            //발급받은 jwt를 저장해주는 함수
//
//            saveJwt(user.id)
//        }
//        if (user.toString().isEmpty()) {
//            Toast.makeText(this, "회원정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
//        }
    }
    }
