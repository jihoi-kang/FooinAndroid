package com.fooin.android.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.fooin.android.base.BaseViewModel
import com.fooin.android.data.model.Restaurant
import com.fooin.android.model.UiRestaurantModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _restaurant = MutableLiveData<UiRestaurantModel>()
    val restaurant: LiveData<UiRestaurantModel> get() = _restaurant

    private val _openLinkEvent = MutableLiveData<String>()
    val openLinkEvent: LiveData<String> get() = _openLinkEvent

    init {
        _restaurant.value = savedStateHandle[ARGUMENT_RESTAURANT]
    }

    fun openLink() {
        val restaurant = restaurant.value ?: return

        _openLinkEvent.value = restaurant.link
    }

    companion object {

        const val ARGUMENT_RESTAURANT = "ARGUMENT_RESTAURANT"

    }

}