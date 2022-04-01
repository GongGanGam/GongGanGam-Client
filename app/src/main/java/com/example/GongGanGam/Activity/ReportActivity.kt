package com.example.GongGanGam.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.gonggangam.R

class ReportActivity : AppCompatActivity() {
    val reports = resources.getStringArray(R.array.spinner_report)

//    val reportSpinnerAdpater = object: ArrayAdapter<String>(this, R.layout.spinner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        initListener()
    }

    private fun initListener() {

    }

}