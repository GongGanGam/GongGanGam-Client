package com.example.GongGanGam.adapter

import com.example.GongGanGam.util.MyPageNoticeAnimation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.GongGanGam.model.NoticeModel
import com.example.gonggangam.R
import com.example.gonggangam.databinding.ItemMypageNoticeListBinding

class MypageNoticeRVAdapter (private val noticelist: ArrayList<NoticeModel>): RecyclerView.Adapter<MypageNoticeRVAdapter.ViewHolder>() {

    inner class ViewHolder( val binding: ItemMypageNoticeListBinding ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notice: NoticeModel) {
            val contentExpand = itemView.findViewById<LinearLayout>(R.id.mypage_notice_content_layout)
            val constraint = itemView.findViewById<ConstraintLayout>(R.id.item_mypage_notice_constraint)

            binding.itemMypageNoticeTitleTv.text = notice.data.title
            binding.itemMypageNoticeDateTv.text = notice.data.noticeDate
            binding.mypageNoticeContentTv.text = notice.data.noticeContent

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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMypageNoticeListBinding = ItemMypageNoticeListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticelist[position])
    }

    override fun getItemCount(): Int {
        return noticelist.size
    }

}