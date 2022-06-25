package com.example.gonggangam.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gonggangam.Class.Diary
import com.example.gonggangam.R
import com.example.gonggangam.Util.ImageLoader
import com.example.gonggangam.databinding.ItemReceivedLetterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(diaries[position])

        holder.binding.itemReceivedLetterCl.setOnClickListener {
            onItemClickListener.onItemClick(diaries[position])
        }
    }

    override fun getItemCount(): Int = diaries.size

    inner class ViewHolder(val binding: ItemReceivedLetterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(diary: Diary) {
            binding.itemReceivedLetterUserName.text = diary.userNickname
            binding.itemReceivedLetterDate.text = diary.diaryDate
            binding.itemReceivedLetterContent.text = diary.diaryContents
            // new iv
            if(diary.isRead == 'F') { // 안읽음
                binding.itemReceivedLetterNewIv.visibility = View.VISIBLE
            }
            else if (diary.isRead == 'T'){ // 읽음
                binding.itemReceivedLetterNewIv.visibility = View.GONE
            }

            // profile iv
            if(diary.userProfImg == null) {
                binding.itemReceivedLetterIv.setImageResource(R.drawable.default_profile_img)
            }
            else {
                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        ImageLoader.loadImage(diary.userProfImg!!)
                    }
                    binding.itemReceivedLetterIv.setImageBitmap(bitmap)
                }
            }

        }

    }
}