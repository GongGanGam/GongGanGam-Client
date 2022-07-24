package com.example.gonggangam.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.request.RequestOptions
import com.example.gonggangam.R

object BindingAdapter {

    @JvmStatic
    fun loadEmoji(emoji: String, imageView: ImageView) {
        when (emoji) {
            "depressed" -> imageView.setImageResource(R.drawable.emoji_depressed)
            "annoyed" -> imageView.setImageResource(R.drawable.emoji_annoyed)
            "boring" -> imageView.setImageResource(R.drawable.emoji_boring)
            "complicated" -> imageView.setImageResource(R.drawable.emoji_complicated)
            "embarrassing" -> imageView.setImageResource(R.drawable.emoji_embarrassing)
            "excited" -> imageView.setImageResource(R.drawable.emoji_excited)
            "fun" -> imageView.setImageResource(R.drawable.emoji_fun)
            "happy" -> imageView.setImageResource(R.drawable.emoji_happy)
            "sad" -> imageView.setImageResource(R.drawable.emoji_sad)
            "soso" -> imageView.setImageResource(R.drawable.emoji_soso)
            "upset" -> imageView.setImageResource(R.drawable.emoji_upset)
            "wonder" -> imageView.setImageResource(R.drawable.emoji_wonder)
        }
    }

    @JvmStatic
    fun loadDiaryImage(url: String, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .into(imageView)
    }

    @JvmStatic
    fun loadProfileImage(url: String?, imageView: ImageView, placeholder: Drawable) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .circleCrop()
            .into(imageView)
    }
}