package com.example.GongGanGam

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.GongGanGam.databinding.FragmentDiaryBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner.THIS_MONTH
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*


class DiaryFragment : Fragment() {
    lateinit var binding: FragmentDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        initListener()
        //달력의 범위 정하기
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth
        val lastMonth = currentMonth.plusYears(10)
        //달력의 첫번째 요일 정하기
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.diaryCalendarViewCv.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.diaryCalendarViewCv.scrollToMonth(currentMonth)

        binding.diaryCalendarViewCv.daySize=Size(240,460)
        class  DayViewContainer(view: View) : ViewContainer(view) {
            val dateText = view.findViewById<TextView>(R.id.calendar_date_text_tv)
            val dateImg=view.findViewById<ImageView>(R.id.calendar_date_img_iv)
            val dateLayout=view.findViewById<ConstraintLayout>(R.id.calendar_date_layout)
            val month=view.findViewById<CalendarView>(R.id.diary_calendarView_cv)
            // Will be set when this container is bound


            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    // Use the CalendarDay associated with this container.
                }
            }
        }


        //달력 month 헤더 부분 커스텀
        class MonthViewContainer(view: View) : ViewContainer(view) {
            val monthText = view.findViewById<TextView>(R.id.calendar_month_header_tv)
        }
        binding.diaryCalendarViewCv.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.monthText.text = "${month.year}년 ${month.yearMonth.monthValue}월"

            }
        }

        //날짜 바인딩
//        binding.exOneCalendar.dayBinder = object : DayBinder<DayViewContainer> {
//            override fun create(view: View) = DayViewContainer(view)
//            override fun bind(container: DayViewContainer, day: CalendarDay) {
//                container.day = day
//                val textView = container.textView
//                textView.text = day.date.dayOfMonth.toString()
//                if (day.owner == DayOwner.THIS_MONTH) {
//                    when {
//                        selectedDates.contains(day.date) -> {
//                            textView.setTextColorRes(R.color.example_1_bg)
//                            textView.setBackgroundResource(R.drawable.example_1_selected_bg)
//                        }
//                        today == day.date -> {
//                            textView.setTextColorRes(R.color.example_1_white)
//                            textView.setBackgroundResource(R.drawable.example_1_today_bg)
//                        }
//                        else -> {
//                            textView.setTextColorRes(R.color.example_1_white)
//                            textView.background = null
//                        }
//                    }
//                } else {
//                    textView.setTextColorRes(R.color.example_1_white_light)
//                    textView.background = null
//                }
//            }
//        }

        binding.diaryCalendarViewCv.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) =  DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container:  DayViewContainer, day: CalendarDay) {
                // Set the calendar day for this container.
                container.day = day
                // Set the date text
                container.dateText.text = day.date.dayOfMonth.toString()
                if (day.owner != THIS_MONTH) {
                    container.dateText.visibility= INVISIBLE
                    container.dateImg.visibility= INVISIBLE
                }

                // Other binding logic
            }

        }






        return binding.root
    }

    private fun initListener() {
//        binding.diaryBtn.setOnClickListener {
//            val intent = Intent(activity, DiaryWriteActivity::class.java)
//            startActivity(intent)
//        }
        //헤더 내의 텍스트는 보이지 않고, 헤더만큼의 공간만 차지하기 위함
        binding.diaryLayoutHeader.layoutHeaderBackIv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderBtnTv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderMenuIv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderTitleTv.visibility= INVISIBLE
    }
    fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val daysOfWeek = DayOfWeek.values()
        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        // Only necessary if firstDayOfWeek is not DayOfWeek.MONDAY which has ordinal 0.
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            return  rhs + lhs
        }
        return daysOfWeek
    }


}

