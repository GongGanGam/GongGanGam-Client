package com.example.GongGanGam


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.GongGanGam.databinding.ActivityDiaryWriteEmojiBinding
import com.example.GongGanGam.databinding.ItemDiaryWriteEmojiBinding


class DiaryWriteEmojiRVAdapter(private val emojis : ArrayList<Emoji>):RecyclerView.Adapter<DiaryWriteEmojiRVAdapter.ViewHolder>(){
    lateinit var binding:ActivityDiaryWriteEmojiBinding

    inner class ViewHolder(val binding: ItemDiaryWriteEmojiBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(emoji: Emoji){
            binding.diaryWriteEmojiEmojiIv.setImageResource(emoji.emoji!!)
            binding.diaryWriteEmojiEmojiTv.text = emoji.state


//
//            binding.diaryWriteEmojiEmojiIv.setOnClickListener{
//                val btn01 = itemView.findViewById<Button>(R.id.diary_write_emoji_unselect_btn)
//                val btn02 = itemView.findViewById<Button>(R.id.diary_write_emoji_selected_btn)
//
//                btn01.setvisibility = View.GONE
//                btn02.visibility = View.VISIBLE
//            }

        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDiaryWriteEmojiBinding = ItemDiaryWriteEmojiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = emojis.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(emojis[position])

    }



}