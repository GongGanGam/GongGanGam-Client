package com.example.GongGanGam.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.GongGanGam.diaryService.BasicResponse
import com.example.GongGanGam.diaryService.DiaryRetrofitInterface
import com.example.GongGanGam.diaryService.Report
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityReportBinding
import com.example.gonggangam.databinding.ItemReportSpinnerBinding
import com.example.GongGanGam.util.getJwt
import com.example.GongGanGam.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class ReportActivity : AppCompatActivity(),  AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityReportBinding

    var reportUserName: String = ""
    var reportType: String = "" // chat / diary / answer
    var idxOfType: Int = 0 // diaryIdx / answerIdx / chat=0
    var reportDetail: Int = 0 // 1 욕설비방 / 2 혐오발언 / 3 불쾌감 / 4 기타
    var reportContent: String? = null //  기타 상세 내용

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        binding = ActivityReportBinding.inflate(layoutInflater)

        // 이전 화면에서 넘어온 data set
        reportType = intent.getStringExtra("reportType")!!
        idxOfType = intent.getIntExtra("idxOfType", 0)
        reportUserName = intent.getStringExtra("reportUserName")!!
        binding.reportSentenceTv.text = "'$reportUserName'님을 신고합니다."

        initSpinner()
        initListener()

        setContentView(binding.root)
    }

    private fun startReport() {
        val report = Report(reportType, idxOfType, reportDetail, reportContent)
        val diaryService = getRetrofit().create(DiaryRetrofitInterface::class.java)

        diaryService.sendReport(getJwt(this), report).enqueue(object: Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful && response.code() == 200) {
                    val resp = response.body()!!
                    Log.d("TAG/API-RESPONSE", resp.toString())

                    when(resp.code) {
                        1000 -> {
                            Log.d("TAG/API-RESULT", resp.message)
                            finish()
                        }
                        else -> Log.d("TAG/API-RESULT", "신고 실패" )
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("TAG/API-ERROR", t.message.toString())
            }
        })
    }

    private fun initSpinner() {
        binding.reportReasonSpinner.adapter = ReportSpinnerAdapter()
        binding.reportReasonSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // footer btn active 처리
                if(position == 0) {
                    binding.reportDoneBtn.setBackgroundResource(R.drawable.button_inactive_background)
                    binding.reportDoneBtnTv.setTextColor(resources.getColor(R.color.inactiveBtnColor))
                    binding.reportDoneBtn.isEnabled = false
                }
                else {
                    binding.reportDoneBtn.setBackgroundResource(R.drawable.button_active_background)
                    binding.reportDoneBtnTv.setTextColor(resources.getColor(R.color.primaryColor))
                    binding.reportDoneBtn.isEnabled = true
                }

                // 상세내용 작성 visibility
                if(position == 4) { // 기타
                    binding.reportEtcEt.visibility = View.VISIBLE
                }
                else {
                    binding.reportEtcEt.visibility = View.GONE
                }
                reportDetail = position
                Log.d("TAG-REPORT", reportDetail.toString())
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun initListener() {
        reportContent = binding.reportEtcEt.text.toString()

        binding.layoutHeaderCloseIv.setOnClickListener {
            finish()
        }

        binding.reportDoneBtn.setOnClickListener {
            startReport()
        }

    }

    inner class ReportSpinnerAdapter: BaseAdapter() {
        val reports = resources.getStringArray(R.array.spinner_report)

        override fun getCount(): Int = reports.size

        override fun getItem(p0: Int): Any = reports[p0]

        override fun getItemId(p0: Int): Long = 0

        @SuppressLint("ViewHolder", "UseCompatLoadingForDrawables")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val view: View = LayoutInflater.from(p2?.context).inflate(R.layout.item_report_spinner, p2, false)
            val itemBinding = ItemReportSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            itemBinding.itemReportTitle.apply {
                text = reports[position]

                if(parent is Spinner) { // 선택된 후
//                    background = context.getDrawable(R.drawable.edittext_rounded_corner_rectangle)
                    itemBinding.itemReportBg.setBackgroundResource(R.drawable.edittext_rounded_corner_rectangle)
                    itemBinding.itemReportDownIv.visibility = View.VISIBLE
                    if(parent.selectedItemPosition == 0) { // 0번을 선택한 후
                        itemBinding.itemReportBg.setBackgroundResource(R.drawable.edittext_rounded_corner_rectangle)
                        text = "사유를 선택해 주세요"
                        setTextColor(Color.parseColor("#9FA8DA"))
                    }
                }
            }


            return itemBinding.root
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val itemBinding = ItemReportSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            itemBinding.itemReportTitle.apply {
                text = reports[position]
            }
            return itemBinding.root
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {

        // p0!!.item_report_bg.setBackgroundResource(R.drawable.button_active_background)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
    fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    fun View.setHeight(context : Context, value: Int) {
        val lp = layoutParams
        lp?.let {
            lp.height = dpToPx(value.toFloat())
            layoutParams = lp
        }
    }
}