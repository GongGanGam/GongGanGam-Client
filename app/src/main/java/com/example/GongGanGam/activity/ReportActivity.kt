package com.example.GongGanGam.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ActivityReplyToDiaryBinding
import com.example.gonggangam.databinding.ActivityReportBinding
import com.example.gonggangam.databinding.ItemReportSpinnerBinding
import kotlinx.android.synthetic.main.item_report_spinner.view.*

class ReportActivity : AppCompatActivity(),  AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        binding = ActivityReportBinding.inflate(layoutInflater)

        initSpinner()
        initListener()

        setContentView(binding.root)
    }

    private fun initSpinner() {
        binding.reportReasonSpinner.adapter = ReportSpinnerAdapter()
        binding.reportReasonSpinner.onItemSelectedListener = this
    }

    private fun initListener() {
        binding.layoutHeaderCloseIv.setOnClickListener {
            finish()
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

                if(parent is Spinner) {
//                    background = context.getDrawable(R.drawable.background_spinner)
                    if(parent.selectedItemPosition == 0) {
                        text = "사유를 선택해 주세요"
//                        background = context.getDrawable(R.drawable.background_spinner)
                        setTextColor(Color.parseColor("#9FA8DA"))
                        parent.setHeight(context, 46)
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