package com.fooin.android.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fooin.android.BR

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutRes: Int,
    private val viewModelClass: Class<VM>,
) : AppCompatActivity() {

    protected lateinit var binding: B

    protected val viewModel: VM by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        with(binding) {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.vm, viewModel)
        }
    }
}