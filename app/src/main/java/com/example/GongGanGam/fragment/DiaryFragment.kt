package com.example.GongGanGam.fragment

import android.annotation.SuppressLint
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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.GongGanGam.activity.DiaryReadActivity
import com.example.GongGanGam.activity.DiaryWriteEmojiActivity
import com.example.GongGanGam.diaryService.DayCell
import com.example.GongGanGam.diaryService.DayResponse
import com.example.GongGanGam.diaryService.DayResponseList
import com.example.GongGanGam.diaryService.DiaryRetrofitInterface
import com.example.GongGanGam.util.getRetrofit
import com.example.gonggangam.R
import com.example.gonggangam.databinding.FragmentDiaryBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread


class DiaryFragment : Fragment() {
    lateinit var binding: FragmentDiaryBinding
    lateinit var allMonth: DayResponseList
    var previousM = ArrayList<DayCell>()
    var currentM = ArrayList<DayCell>()
    var nextM = ArrayList<DayCell>()
    var currentMonth = YearMonth.now()
    var firstMonth = currentMonth.minusYears(5)
    var lastMonth = currentMonth.plusYears(5)

    //글쓴 후 리로드 위함
    override fun onResume() {
        super.onResume()
        dataLoad(currentMonth.year, currentMonth.monthValue)
        binding.diaryCalendarViewCv.notifyMonthChanged(currentMonth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        initListener(Year.now().value, YearMonth.now().monthValue)


        //요일 바인딩. 일요일을 처음으로
        fun daysOfWeekFromLocale(): Array<DayOfWeek> {
            val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
            val daysOfWeek = DayOfWeek.values()
            if (firstDayOfWeek != DayOfWeek.MONDAY) {
                val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
                val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
                return  rhs+lhs
            }
            return daysOfWeek
        }

        binding.diaryLayoutHeader.layoutHeaderBackIv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderBtnTv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderMenuIv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderTitleTv.visibility= INVISIBLE

        //달력의 첫번째 요일 정하기
        val daysOfWeek = daysOfWeekFromLocale()
        binding.diaryCalendarViewCv.setup(firstMonth, lastMonth, daysOfWeek.first())
        binding.diaryCalendarViewCv.notifyCalendarChanged()
        binding.diaryCalendarViewCv.scrollToDate(LocalDate.now())

        binding.diaryCalendarViewCv.maxRowCount=6

        //날짜 바인딩 class
        class  DayViewContainer(view: View) : ViewContainer(view) {
            val dateText = view.findViewById<TextView>(R.id.calendar_date_text_tv)
            val dateImg=view.findViewById<ImageView>(R.id.calendar_date_img_iv)
            val dateLayout=view.findViewById<ConstraintLayout>(R.id.calendar_date_layout)


            lateinit var day: CalendarDay

            init {

                view.setOnClickListener {
                    if(day.owner == DayOwner.THIS_MONTH) {
                        if (day.date.year > LocalDate.now().year || (day.date.year == LocalDate.now().year) && (day.date.dayOfYear > LocalDate.now().dayOfYear)) {
                            Toast.makeText(context, "오늘과 이전 날짜의 일기만 작성할 수 있어요!", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                    else if(day.owner != DayOwner.THIS_MONTH){
                        Toast.makeText(context,"달력을 넘겨 클릭해주세요!",Toast.LENGTH_SHORT).show()
                        Log.d("선택",day.date.dayOfMonth.toString()+","+day.date.monthValue.toString())
                    }
                }
            }
        }
        //버튼 클릭이 아니라 드래그로 달력 넘길떄
        binding.diaryCalendarViewCv.monthScrollListener= { month ->

            dataLoad(month.year, month.month)
            currentMonth=month.yearMonth
            binding.diaryCalendarViewCv.smoothScrollToMonth(month.yearMonth)

        }

        //날짜 개별 뷰 설정
        binding.diaryCalendarViewCv.dayBinder = object : DayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                // Set the calendar day for this container.

                container.day = day

                fun emojiBinding(dayArr:ArrayList<DayCell>,isActive:Boolean) {
                    val dayIterator = dayArr.listIterator()
                    while (dayIterator.hasNext()) {
                        val tmp: DayCell = dayIterator.next()
                        if (tmp.day.toInt() == day.date.dayOfMonth.toString().toInt()
                        ) {
                            //현재 달이라면
                            if(isActive) {

                                // emoji 존재 -> 다이어리 읽기
                                container.dateImg.setOnClickListener {
                                    goToReadDiaryView(day.date.year, day.date.monthValue, day.day)
                                }
                                when (tmp.emoji) {
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
                            }else{
                                when (tmp.emoji) {
                                    "depressed" -> container.dateImg.setImageResource(R.drawable.emoji_dark_depressed)
                                    "annoyed" -> container.dateImg.setImageResource(R.drawable.emoji_dark_annoyed)
                                    "boring" -> container.dateImg.setImageResource(R.drawable.emoji_dark_boring)
                                    "complicated" -> container.dateImg.setImageResource(
                                        R.drawable.emoji_dark_complicated
                                    )
                                    "embarrassing" -> container.dateImg.setImageResource(
                                        R.drawable.emoji_dark_embarrassing
                                    )
                                    "excited" -> container.dateImg.setImageResource(R.drawable.emoji_dark_excited)
                                    "fun" -> container.dateImg.setImageResource(R.drawable.emoji_dark_fun)
                                    "happy" -> container.dateImg.setImageResource(R.drawable.emoji_dark_happy)
                                    "sad" -> container.dateImg.setImageResource(R.drawable.emoji_dark_sad)
                                    "soso" -> container.dateImg.setImageResource(R.drawable.emoji_dark_soso)
                                    "upset" -> container.dateImg.setImageResource(R.drawable.emoji_dark_upset)
                                    "wonder" -> container.dateImg.setImageResource(R.drawable.emoji_dark_wonder)

                                }
                            }
                        }
                    }
                }

                // Set the date text
                container.dateText.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) { //현재 달 범위 내라면
                    // Show the month dates. Remember that views are recycled!

                    container.dateText.setTextColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.calendar_active
                        )
                    )
                    container.dateImg.setImageResource(R.drawable.calendar_date_non)

                    // 이모지 미존재 -> 다이어리 쓰기
                    container.dateImg.setOnClickListener {
                        goToWriteDiaryView(day.date.year, day.date.monthValue, day.day)
                    }


                    // If this is the selected date, show a round background and change the text color.

                    if (currentM.isNotEmpty()) {
                        emojiBinding(currentM,true)
                    } else {
                        container.dateImg.setImageResource(R.drawable.calendar_date_non)
                    }
                }



                else if (day.owner != DayOwner.THIS_MONTH) {

                    container.dateText.setTextColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.calendar_inactive
                        )
                    )
                    container.dateImg.setImageResource(R.drawable.calendar_disable_icon)
                    if (day.date.monthValue.toString()
                            .toInt() == currentMonth.minusMonths(1).monthValue
                    ) {
                        if (previousM.isNotEmpty()) {
                            emojiBinding(previousM,false)
                        }else{
                            container.dateImg.setImageResource(R.drawable.calendar_disable_icon)
                        }
                    }

                    else if (day.date.monthValue.toString()
                            .toInt() == currentMonth.plusMonths(1).monthValue

                    ) {
                        if (nextM.isNotEmpty()) {
                            emojiBinding(nextM,false)
                        }else{
                            container.dateImg.setImageResource(R.drawable.calendar_disable_icon)
                        }
                    }
                }

                //날짜 뷰 크기 설정
                val calendar = binding.diaryCalendarViewCv.height
                val dayWidth = binding.diaryCalendarViewCv.daySize.width
                container.dateLayout.layoutParams =
                    LinearLayout.LayoutParams(dayWidth, calendar / 8)
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
            @SuppressLint("SetTextI18n")
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.monthText.text = "${month.year}년 ${month.yearMonth.monthValue}월"


                container.monthPrev.setOnClickListener {
                    currentMonth=currentMonth.minusMonths(1)
                    binding.diaryCalendarViewCv.smoothScrollToMonth(currentMonth)
                    dataLoad(currentMonth.year,currentMonth.monthValue)

                }
                container.monthNext.setOnClickListener {
                    currentMonth=currentMonth.plusMonths(1)
                    binding.diaryCalendarViewCv.smoothScrollToMonth(currentMonth)
                    dataLoad(currentMonth.year,currentMonth.monthValue)
                }


            }

        }



        return binding.root
    }

    //캘린더에 변화 생길 때마다 해당 년, 별에 맞는 데이터 불러오는 함수
    private fun dataLoad(thisY:Int,thisM:Int){
        previousM.clear()
        currentM.clear()
        nextM.clear()
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)
        thread {
            val response = diaryService.getCalendar(thisY, thisM).execute()
            CoroutineScope(Dispatchers.Main).launch {
                if (response.isSuccessful && response.code() == 200) {
                    val resp : DayResponse? =response.body()!!
                    when(resp!!.code) {
                        1000 -> allMonth = resp.result
                        else -> Log.d("TAG/API-CODE", "실패" )
                    }
                    Log.d("TAG-API", allMonth.toString())

                    previousM = allMonth.previous//이전 달 list
                    currentM  = allMonth.current //현재 달 list
                    nextM  = allMonth.next //다음 달 list


                } else if(response.code() == 2000||response.code() == 3000||response.code() == 5001||response.code() == 5002){ //  response.code == 2000,3000,5001,5002
                    Log.d("Retrofit", "onResponse 실패"+response.code())
                }

                else {
                    // 실패 처리
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("Retrofit", "onFailure 에러: "+ response.code().toString())
                }
                binding.diaryCalendarViewCv.notifyMonthChanged(currentMonth)

            }

        }



    }

    private fun initListener(thisY:Int,thisM:Int) {

        //헤더 내의 텍스트는 보이지 않고, 헤더만큼의 공간만 차지하기 위함
        binding.diaryLayoutHeader.layoutHeaderBackIv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderBtnTv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderMenuIv.visibility= INVISIBLE
        binding.diaryLayoutHeader.layoutHeaderTitleTv.visibility= INVISIBLE


        dataLoad(thisY, thisM)

    }

    private fun goToWriteDiaryView(year: Int, month: Int, day: Int) {
        val intent = Intent(requireContext(), DiaryWriteEmojiActivity::class.java)
        intent.putExtra("year", year)
        intent.putExtra("month",month)
        intent.putExtra("day", day)

        startActivity(intent)
    }

    private fun goToReadDiaryView(year: Int, month: Int, day: Int) {
        val intent = Intent(requireContext(), DiaryReadActivity::class.java)
        intent.putExtra("year", year)
        intent.putExtra("month",month)
        intent.putExtra("day", day)

        startActivity(intent)
    }

}






