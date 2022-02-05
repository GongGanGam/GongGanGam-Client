package com.example.GongGanGam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.GongGanGam.databinding.FragmentLetterBinding
import com.google.android.material.tabs.TabLayoutMediator


class LetterFragment : Fragment() {
    lateinit var binding:FragmentLetterBinding
    val tabTitleArray = arrayListOf("받은 일기", "받은 답장")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLetterBinding.inflate(inflater, container, false)

        initTabToViewPager()

        return binding.root
    }

    private fun initTabToViewPager() {
        binding.letterViewPager.adapter = LetterViewPagerAdapter(this)
        binding.letterViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.letterTabLayout, binding.letterViewPager){ tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()


    }

    private inner class LetterViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> ReceivedDiaryFragment()
                1 -> ReceivedReplyFragment()
                else -> ReceivedDiaryFragment()
            }
        }
    }

}