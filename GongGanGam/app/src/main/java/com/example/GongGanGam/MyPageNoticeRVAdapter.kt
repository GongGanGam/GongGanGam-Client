package com.example.GongGanGam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.GongGanGam.R

class MypageNoticeRVAdapter (private val noticelist: List<Notice>): RecyclerView.Adapter<MypageNoticeRVAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(notice: Notice) {
            val title = itemView.findViewById<TextView>(R.id.item_mypage_notice_title_tv)
            val date = itemView.findViewById<TextView>(R.id.item_mypage_notice_date_tv)
            val contentExpand =
                itemView.findViewById<LinearLayout>(R.id.mypage_notice_content_layout)
            val constraint = itemView.findViewById<ConstraintLayout>(R.id.item_mypage_notice_constraint)

            title.text = notice.title
            date.text = notice.date


            constraint.setOnClickListener {
                val show = animationLayout(!notice.isExpanded, it, contentExpand)
                notice.isExpanded = show
            }
        }

        private fun animationLayout(
            isExpanded: Boolean,
            view: View,
            layoutExpand: LinearLayout
        ): Boolean {
            MyPageNoticeAnimation.expandLayout(view, isExpanded)
            val arrowDown = itemView.findViewById<ImageView>(R.id.item_mypage_notice_arrowDown_iv)
            val arrowUp = itemView.findViewById<ImageView>(R.id.item_mypage_notice_arrowUp_iv)
            if (isExpanded) {
                arrowDown.visibility=View.GONE
                arrowUp.visibility=View.VISIBLE
                MyPageNoticeAnimation.expand(layoutExpand)
            } else {
                arrowDown.visibility=View.VISIBLE
                arrowUp.visibility=View.GONE
                MyPageNoticeAnimation.nonexpand(layoutExpand)
            }
            return isExpanded
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_mypage_notice_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticelist[position])
    }

    override fun getItemCount(): Int {
        return noticelist.size
    }

}