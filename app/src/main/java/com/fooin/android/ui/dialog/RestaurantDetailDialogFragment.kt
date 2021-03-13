package com.fooin.android.ui.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.fooin.android.R
import com.fooin.android.base.BaseBottomSheetDialogFragment
import com.fooin.android.databinding.BottomSheetRestaurantDetailBinding
import com.fooin.android.model.UiRestaurantModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailDialogFragment :
    BaseBottomSheetDialogFragment<BottomSheetRestaurantDetailBinding, RestaurantDetailViewModel>(
        R.layout.bottom_sheet_restaurant_detail,
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
        fun newInstance(restaurant: UiRestaurantModel): RestaurantDetailDialogFragment =
            RestaurantDetailDialogFragment().apply {
                arguments = bundleOf(RestaurantDetailViewModel.ARGUMENT_RESTAURANT to restaurant)
            }
    }

}