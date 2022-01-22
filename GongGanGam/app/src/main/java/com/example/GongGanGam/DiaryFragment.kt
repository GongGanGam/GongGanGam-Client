package com.example.GongGanGam

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.GongGanGam.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {
    lateinit var binding: FragmentDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.diaryBtn.setOnClickListener {
            val intent = Intent(activity, DiaryWriteActivity::class.java)
            startActivity(intent)
        }
    }

}