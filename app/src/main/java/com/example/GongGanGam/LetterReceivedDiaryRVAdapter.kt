package com.example.GongGanGam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.GongGanGam.databinding.ItemReceivedLetterBinding

class LetterReceivedDiaryRVAdapter(private val diaries : ArrayList<Diary>) :
    RecyclerView.Adapter<LetterReceivedDiaryRVAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(diary: Diary)
    }

    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        onItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReceivedLetterBinding = ItemReceivedLetterBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterReceivedDiaryRVAdapter.ViewHolder, position: Int) {
        holder.bind(diaries[position])

        holder.binding.itemReceivedLetterCl.setOnClickListener {
            onItemClickListener.onItemClick(diaries[position])
        }
    }

    override fun getItemCount(): Int = diaries.size

    inner class ViewHolder(val binding: ItemReceivedLetterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(diary: Diary) {
            binding.itemReceivedLetterUserName.text = diary.title
            binding.itemReceivedLetterDate.text = diary.date
            binding.itemReceivedLetterContent.text = diary.content
        }

        init {

        }
    }
}