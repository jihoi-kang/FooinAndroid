package com.fooin.android.ext

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("onSearch")
fun EditText.bindOnSearch(listener: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            listener.invoke()
            return@setOnEditorActionListener false
        }
        return@setOnEditorActionListener false
    }
}