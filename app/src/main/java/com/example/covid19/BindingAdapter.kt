package com.example.covid19

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("userProfile")
fun userProfile(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
            .load(url)
            .into(imageView)
}