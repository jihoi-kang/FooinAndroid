package com.fooin.android.ui.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.fooin.android.R
import com.fooin.android.base.BaseBottomSheetDialogFragment
import com.fooin.android.databinding.BottomSheetStoreDetailBinding
import com.fooin.android.model.Restaurant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreDetailDialogFragment :
    BaseBottomSheetDialogFragment<BottomSheetStoreDetailBinding, StoreDetailViewModel>(
        R.layout.bottom_sheet_store_detail,
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserve()
    }

    private fun setupObserve() {
        viewModel.openLinkEvent.observe(this) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            dismissAllowingStateLoss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(restaurant: Restaurant): StoreDetailDialogFragment =
            StoreDetailDialogFragment().apply {
                arguments = bundleOf(StoreDetailViewModel.ARGUMENT_RESTAURANT to restaurant)
            }
    }

}