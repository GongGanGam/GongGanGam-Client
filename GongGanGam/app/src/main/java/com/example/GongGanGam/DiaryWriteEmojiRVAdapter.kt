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


class DiaryWriteEmojiRVAdapter(private val emojilist : ArrayList<Emoji>):RecyclerView.Adapter<DiaryWriteEmojiRVAdapter.ViewHolder>(){

    interface MyItemClickListener{
        fun onItemClick(emoji: Emoji)
    }

    private lateinit var  mitemClickListener:MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mitemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemDiaryWriteEmojiBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(emoji: Emoji){
            binding.diaryWriteEmojiEmojiIv.setImageResource(emoji.emoji!!)
            binding.diaryWriteEmojiEmojiTv.text = emoji.state

        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDiaryWriteEmojiBinding = ItemDiaryWriteEmojiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = emojilist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(emojilist[position])
        holder.itemView.setOnClickListener{ mitemClickListener.onItemClick(emojilist[position])}
    }



}