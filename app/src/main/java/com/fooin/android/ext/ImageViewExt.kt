package com.fooin.android.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}