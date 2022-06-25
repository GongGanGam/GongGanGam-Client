package com.example.gonggangam.Fragment




import android.app.Activity
import android.content.Context
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
import com.example.gonggangam.Activity.DiaryWriteEmojiActivity
import com.example.gonggangam.DiaryService.*
import com.example.gonggangam.R
import com.example.gonggangam.databinding.FragmentDiaryBinding
import com.example.gonggangam.getJwt
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


import retrofit2.adapter.rxjava2.Result.response
import com.example.gonggangam.Activity.MainActivity







class DiaryFragment : Fragment() {
    lateinit var binding: FragmentDiaryBinding
    lateinit var jwt: String
    private var isSelected : Boolean = false
    private val dateList : Array<Int>? = null
    private var diaries = ArrayList<DayCell>()
    lateinit var allMonth: DayResponseList
    var previousM = ArrayList<DayCell>()
     var currentM = ArrayList<DayCell>()
    var nextM = ArrayList<DayCell>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        jwt = getJwt(requireContext())

        initListener()

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

        //달력의 범위 정하기
//        var currentMonth = YearMonth.now()
        var currentMonth = YearMonth.now()
        var firstMonth = currentMonth.minusMonths(5)
        var lastMonth = currentMonth.plusYears(10)




        //달력의 첫번째 요일 정하기
        val daysOfWeek = daysOfWeekFromLocale()
        binding.diaryCalendarViewCv.setup(firstMonth, lastMonth, daysOfWeek.first())

        binding.diaryCalendarViewCv.scrollToDate(LocalDate.now())
        binding.diaryCalendarViewCv.notifyCalendarChanged()
        binding.diaryCalendarViewCv.maxRowCount=6

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
                    if (day.owner == DayOwner.THIS_MONTH&&day.date.dayOfMonth.toString().toInt()<= LocalDate.now().dayOfMonth){
                        // Show the month dates. Remember that views are recycled!
//                        if (day.date == selectedDate) {
                            // If this is the selected date, show a round background and change the text color.
//                            if(dayList==null||dayList!![day.date.dayOfMonth.toString().toInt()]==null){
                                Log.d("오늘", LocalDate.now().dayOfMonth.toString())
                                Log.d("선택",day.date.dayOfMonth.toString())

//                        if(currentM){ //배열에 포함되는지
                            val eActivity = activity as MainActivity
                            eActivity.receiveData(day.date.year.toInt(),day.date.monthValue.toInt(),day.day.toInt())

//                        }

//                            }else{
////                           val intent = Intent(activity, DiaryWriteEmojiActivity::class.java)
////                            startActivity(intent)
//
//                            }
                        }else if(day.owner == DayOwner.THIS_MONTH&&day.date.dayOfMonth.toString().toInt()> LocalDate.now().dayOfMonth){

                        Toast.makeText(context,"오늘과 이전 날짜의 일기만 작성할 수 있어요!",Toast.LENGTH_SHORT).show()
                    }
                    else if(day.owner != DayOwner.THIS_MONTH){
                        Toast.makeText(context,"달력을 넘겨 클릭해주세요!",Toast.LENGTH_SHORT).show()
                        Log.d("선택",day.date.dayOfMonth.toString()+","+day.date.monthValue.toString())
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

                if (day.owner == DayOwner.THIS_MONTH){
                // Show the month dates. Remember that views are recycled!
//                    container.dateImg.setImageResource(R.drawable.calendar_date_non)
                    container.dateText.setTextColor(ContextCompat.getColor(context!!, R.color.calendar_active))

//                    if (day.date == selectedDate) {

                        // If this is the selected date, show a round background and change the text color.
                        Log.d("작동1", "작동1")
                        if(currentM.isNotEmpty()){
//                        var currentIterator = currentM?.listIterator()

//                        while (currentIterator?.hasNext()) {
                        for(tmp: DayCell in currentM){
                            if (tmp.day.toInt() == day.date.dayOfMonth.toString().toInt()) {
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
                            }
                        }
                    }else{
                            container.dateImg.setImageResource(R.drawable.calendar_date_non)
                        }
                }
//


                else if(day.owner != DayOwner.THIS_MONTH) { //월별 범위

                    container.dateText.setTextColor(
                        ContextCompat.getColor(
                            context!!,
                            R.color.calendar_inactive
                        )
                    )
                    container.dateImg.setImageResource(R.drawable.calendar_disable_icon)
                    if (day.date.monthValue.toString().toInt() == currentMonth.minusMonths(1).monthValue
                    ) {
                        if (previousM.isNotEmpty()) {
                            var previousIterator = previousM?.listIterator()

                            while (previousIterator.hasNext() ) {
                                val tmp: DayCell = previousIterator.next()
                                if (tmp.day.toInt() == day.date.dayOfMonth.toString().toInt()
                                ) {
                                    when (tmp.emoji) {
                                        "depressed" -> container.dateImg.setImageResource(R.drawable.emoji_dark_depressed)
                                        "annoyed" -> container.dateImg.setImageResource(R.drawable.emoji_dark_annoyed)
                                        "boring" -> container.dateImg.setImageResource(R.drawable.emoji_dark_boring)
                                        "complicated" -> container.dateImg.setImageResource(R.drawable.emoji_dark_complicated)
                                        "embarrassing" -> container.dateImg.setImageResource(R.drawable.emoji_dark_embarrassing)
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
                    } else if (day.date.monthValue.toString().toInt() == currentMonth.plusMonths(1).monthValue

                    ) {
                        if (nextM.isNotEmpty()) {
                            var nextIterator = nextM?.listIterator()

                            while (nextIterator.hasNext()) {
                                val tmp: DayCell = nextIterator.next()
                                if (tmp.day.toInt() == day.date.dayOfMonth.toString().toInt()
                                ) {
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
                }

//                //date height
//                val size = (context!!.resources.getDimension(R.id.diary_calendarView_cv.takeHighestOneBit().toInt()) / 6).toInt()
                                    val calendar = binding.diaryCalendarViewCv.height
                                    val dayWidth = binding.diaryCalendarViewCv.daySize.width
                                    container.dateLayout.layoutParams =
                                        LinearLayout.LayoutParams(dayWidth, calendar / 8)
//                container.dateLayout.layoutParams = LinearLayout.LayoutParams(dayWidth, dayWidth*3/2)

//                // Other binding logic
//            }

                                }}





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
        diaryService.getCalendar(jwt,Year.now().value, YearMonth.now().monthValue)
            .enqueue(object : Callback<DayResponse> {
                override fun onResponse(
                    call: Call<DayResponse>,
                    response: Response<DayResponse>
                ) {
                    if (response.isSuccessful && response.code() == 200) { //  response.code == 1000
                        // 성공 처리

                        var resp : DayResponse? =response.body()!!
                        when(resp!!.code) {
                            1000 -> allMonth = resp!!.result
                            else -> Log.d("TAG/API-CODE", "실패" )
                        }
                        Log.d("TAG-API", allMonth.toString())

                         previousM = allMonth?.previous//이전 달 list
                         currentM  = allMonth?.current //현재 달 list
                         nextM  = allMonth?.next //다음 달 list

                        Log.d("response 받아왔는지", resp.toString())
                        Log.d("Retrofit", "onResponse 성공")
                        Log.d("됨", "onResponse 성공")
                        Log.d("년 달", Year.now().value.toInt().toString()+YearMonth.now().monthValue.toInt().toString())

                        Log.d("이전달", previousM.toString())
                        Log.d("현재", currentM.toString())
                        Log.d("다음달", nextM.toString())
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
            })

    }
    }






