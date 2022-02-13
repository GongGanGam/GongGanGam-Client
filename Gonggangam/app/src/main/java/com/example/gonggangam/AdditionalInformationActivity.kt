package com.example.gonggangam

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.GongGanGam.R
import com.example.GongGanGam.databinding.ActivityAdditionalInformationBinding

class AdditionalInformationActivity() : AppCompatActivity() {
    lateinit var binding: ActivityAdditionalInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionalInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ButtonChange()


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
        val nickName: String = binding.additionalNickInputEt.text.toString()
        val birthYear: String = binding.additionalBirthYearInputTv.text.toString()
        lateinit var gender: String

        if (binding.additionalGenderMaleBtn.isSelected) {
            gender = binding.additionalGenderMaleBtn.text.toString()
        } else if (binding.additionalGenderFemaleBtn.isSelected) {
            gender = binding.additionalGenderFemaleBtn.text.toString()
        } else if (binding.additionalGenderNoBtn.isSelected) {
            gender = binding.additionalGenderNoBtn.text.toString()
        }

        Toast.makeText(
            this,
            "nickname $nickName birthYear $birthYear gender $gender",
            Toast.LENGTH_SHORT
        ).show()


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

    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.additionalNickInputEt.windowToken, 0)
        binding.additionalNickInputEt.clearFocus()
    }

    private fun ButtonChange() {
        binding.additionalNickInputEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val nk = binding.additionalNickInputEt.toString().isNotEmpty()
                val bir = binding.additionalBirthYearInputTv.toString().isNotEmpty()
                lateinit var gen: String
                if (binding.additionalGenderMaleBtn.isSelected) {
                    gen = binding.additionalGenderMaleBtn.text.toString()
                } else if (binding.additionalGenderFemaleBtn.isSelected) {
                    gen = binding.additionalGenderFemaleBtn.text.toString()
                } else if (binding.additionalGenderNoBtn.isSelected) {
                    gen = binding.additionalGenderNoBtn.text.toString()
                }

                if (nk && bir && gen.isNotEmpty()) {
                    binding.additionalCompleteBtn.setBackgroundResource(R.drawable.button_active_background)
                    binding.additionalCompleteBtn.setTextColor(resources.getColor(R.color.primaryColor))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
}
