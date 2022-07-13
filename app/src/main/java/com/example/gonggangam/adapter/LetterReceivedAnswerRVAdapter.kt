package com.example.gonggangam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gonggangam.model.Answer
import com.example.gonggangam.R
import com.example.gonggangam.util.ImageLoader
import com.example.gonggangam.databinding.ItemReceivedLetterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LetterReceivedAnswerRVAdapter(private val answers: ArrayList<Answer>):  RecyclerView.Adapter<LetterReceivedAnswerRVAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(answer: Answer)
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
        holder.bind(answers[position])

        holder.binding.itemReceivedLetterCl.setOnClickListener {
            onItemClickListener.onItemClick(answers[position])
        }
    }

    override fun getItemCount(): Int = answers.size

    inner class ViewHolder(val binding: ItemReceivedLetterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answer: Answer) {
            binding.itemReceivedLetterUserName.text = answer.userNickname
            binding.itemReceivedLetterDate.text = answer.answerDate
            binding.itemReceivedLetterContent.text = answer.answerContents
            // new iv
            if(answer.isRead == 'F') { // 안읽음
                binding.itemReceivedLetterNewIv.visibility = View.VISIBLE
            }
            else if (answer.isRead == 'T'){ // 읽음
                binding.itemReceivedLetterNewIv.visibility = View.GONE
            }

            // profile iv
            if(answer.userProfImg == null) {
                binding.itemReceivedLetterIv.setImageResource(R.drawable.default_profile_img)
            }
            else {
                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        ImageLoader.loadImage(answer.userProfImg!!)
                    }
                    binding.itemReceivedLetterIv.setImageBitmap(bitmap)
                }
            }

        }

    }
}