package com.fooin.android.ext

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean) {
    isVisible = visible
}