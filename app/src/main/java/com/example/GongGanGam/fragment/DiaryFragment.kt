package com.example.gonggangam.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.gonggangam.DiaryService.DayCell
import com.example.gonggangam.DiaryService.DayResponse
import com.example.gonggangam.DiaryService.DiaryRetrofitInterface
import com.example.gonggangam.Activity.DiaryWriteEmojiActivity
import com.example.gonggangam.R
import com.example.gonggangam.databinding.FragmentDiaryBinding
import com.example.gonggangam.getRetrofit
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*


class DiaryFragment : Fragment() {
    lateinit var binding: FragmentDiaryBinding
    private var selectedDate: LocalDate? = null
    private var isSelected : Boolean = false
    private var dayList: Array<DayCell>? = null
    private val dateList : Array<Int>? = null
    private lateinit var retrofit: Retrofit
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
//        initListener() // 여기서 오류나요!
        binding.diaryLayoutHeader.layoutHeaderBackIv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderBtnTv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderMenuIv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderTitleTv.visibility= INVISIBLE

        //달력의 범위 정하기
        var currentMonth = YearMonth.now().minusMonths(3)
        var firstMonth = currentMonth
        var lastMonth = currentMonth.plusYears(10)




        //달력의 첫번째 요일 정하기
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.diaryCalendarViewCv.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.diaryCalendarViewCv.scrollToMonth(currentMonth)
        binding.diaryCalendarViewCv.notifyCalendarChanged()
        //        binding.diaryCalendarViewCv.daySize=Size(240,460)


        class  DayViewContainer(view: View) : ViewContainer(view) {
            val dateText = view.findViewById<TextView>(R.id.calendar_date_text_tv)
            val dateImg=view.findViewById<ImageView>(R.id.calendar_date_img_iv)
            val dateLayout=view.findViewById<ConstraintLayout>(R.id.calendar_date_layout)
            val month=view.findViewById<CalendarView>(R.id.diary_calendarView_cv)
            // Will be set when this container is bound


            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {

                    // Check the day owner as we do not want to select in or out dates.
                    if (day.owner == DayOwner.THIS_MONTH) {

                        // Keep a reference to any previous selection
                        // in case we overwrite it and need to reload it.
                        val currentSelection = selectedDate
                        if (currentSelection == day.date) {
                            // If the user clicks the same date, clear selection.
                            selectedDate = null
                            // Reload this date so the dayBinder is called
                            // and we can REMOVE the selection background.
                            binding.diaryCalendarViewCv.notifyDateChanged(currentSelection)
                        } else {
                            selectedDate = day.date
                            // Reload the newly selected date so the dayBinder is
                            // called and we can ADD the selection background.
                            binding.diaryCalendarViewCv.notifyDateChanged(day.date)
                            if (currentSelection != null){
                                // We need to also reload the previously selected
                                // date so we can REMOVE the selection background.
                                binding.diaryCalendarViewCv.notifyDateChanged(currentSelection)
                            }
                        }
                    }


                }
            }
        }

        binding.diaryCalendarViewCv.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) =  DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container:  DayViewContainer, day: CalendarDay) {
                // Set the calendar day for this container.
                container.day = day
                // Set the date text
                container.dateText.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    // Show the month dates. Remember that views are recycled!
                    if (day.date == selectedDate) {
                        // If this is the selected date, show a round background and change the text color.
                        if(dayList==null||dayList!![container.day.date.toString().toInt()]==null){
                            val intent = Intent(activity, DiaryWriteEmojiActivity::class.java)
                            intent.putExtra("year", day.date.year)
                            intent.putExtra("month", day.date.monthValue)
                            intent.putExtra("day",day.day)



                            startActivity(intent)
                        }else{
//                           val intent = Intent(activity, DiaryWriteEmojiActivity::class.java)
//                            startActivity(intent)

                        }
                    }
                }
                else{ //월별 범위 밖
                    container.dateText.visibility= INVISIBLE
                    container.dateImg.visibility= INVISIBLE
                }

                //일기 쓴 날짜 이모지 바꾸기
                if (dayList!=null&& dayList!![day.date.toString().toInt()]!=null) {
                    //일기 쓴 날짜 이모지 바꾸기

                    when (dayList!![day.date.toString().toInt()].emoji) {
                        "depressed" -> container.dateImg.setImageResource(R.drawable.emoji_depressed)
                        "annoyed" -> container.dateImg.setImageResource(R.drawable.emoji_annoyed)
                        "boring" -> container.dateImg.setImageResource(R.drawable.emoji_boring)
                        "complicated" -> container.dateImg.setImageResource(R.drawable.emoji_complicated)
                        "embarrassing" -> container.dateImg.setImageResource(R.drawable.emoji_embarrassing)
                        "excited" -> container.dateImg.setImageResource(R.drawable.emoji_excited)
                        "fun" -> container.dateImg.setImageResource(R.drawable.emoji_fun)
                        "happy" -> container.dateImg.setImageResource(R.drawable.emoji_happy)
                        "sad" -> container.dateImg.setImageResource(R.drawable.emoji_sad)
                        "soso" -> container.dateImg.setImageResource(R.drawable.emoji_soso)
                        "upset" -> container.dateImg.setImageResource(R.drawable.emoji_upset)
                        "wonder" -> container.dateImg.setImageResource(R.drawable.emoji_wonder)

                    }




                } else {
                    // If this is NOT the selected date, remove the background and reset the text color.
                    container.dateImg.setImageResource(R.drawable.calendar_date_non)
                }

//                //date height
                val dayWidth = binding.diaryCalendarViewCv.daySize.width
                container.dateLayout.layoutParams = LinearLayout.LayoutParams(dayWidth, dayWidth*3/2)

//                // Other binding logic
//            }

            }



        }

        //달력 month 헤더 부분 커스텀
        class MonthViewContainer(view: View) : ViewContainer(view) {
            val monthText = view.findViewById<TextView>(R.id.calendar_month_header_tv)
            val monthPrev = view.findViewById<ImageView>(R.id.calendar_left_arrow_iv)
            val monthNext = view.findViewById<ImageView>(R.id.calendar_right_arrow_iv)
        }
        binding.diaryCalendarViewCv.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.monthText.text = "${month.year}년 ${month.yearMonth.monthValue}월"
                container.monthPrev.setOnClickListener {
                    currentMonth=currentMonth.minusMonths(1)
                    binding.diaryCalendarViewCv.scrollToMonth(currentMonth)
                    binding.diaryCalendarViewCv.notifyMonthChanged(currentMonth)


                }
                container.monthNext.setOnClickListener {
                    currentMonth=currentMonth.plusMonths(1)
                    binding.diaryCalendarViewCv.scrollToMonth(currentMonth)
                    binding.diaryCalendarViewCv.notifyMonthChanged(currentMonth)

                }
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

        //retrofit 통신, 이렇게 해주세요!
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)


        diaryService.getCalendar(Year.now().value.toInt(), YearMonth.now().monthValue.toInt())
            .enqueue((object : Callback<DayResponse> {
                override fun onResponse(
                    call: Call<DayResponse>,
                    response: Response<DayResponse>
                ) {
                    if (response.isSuccessful) { //  response.code == 1000
                        // 성공 처리
                        val temp=response?.body()!!.result

                        for(i in temp){
                            dayList?.set(i.day.toInt(), i)
                        }

                        Log.d("Retrofit", "onResponse 성공")
                        Log.d("됨", "onResponse 성공")
                        Log.d("년 달", Year.now().value.toInt().toString()+YearMonth.now().monthValue.toInt().toString())
                    } else { //  response.code == 2000,3000,5001,5002
                        Log.d("Retrofit", "onResponse 실패"+response.code())
                        Log.d("안됨", "onResponse 실패"+response.code())
                    }
                }

                override fun onFailure(call: Call<DayResponse>, t: Throwable) {
                    // 실패 처리
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("Retrofit", "onFailure 에러: "+ t.message.toString())
                }
            }))
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

