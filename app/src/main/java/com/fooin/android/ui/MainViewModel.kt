package com.fooin.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fooin.android.base.BaseViewModel
import com.fooin.android.data.RestaurantRepository
import com.fooin.android.model.UiRestaurantModel
import com.fooin.android.model.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : BaseViewModel() {

    val query = MutableLiveData<String>()

    private val _restaurantItems = MutableLiveData<List<UiRestaurantModel>>(emptyList())
    val restaurantItems: LiveData<List<UiRestaurantModel>> get() = _restaurantItems

    private val _hideKeyboardEvent = MutableLiveData<Unit>()
    val hideKeyboardEvent: LiveData<Unit> get() = _hideKeyboardEvent

    fun getRestaurants() {
        val query = query.value ?: return

        viewModelScope.launch {
            _hideKeyboardEvent.value = Unit
            val result = repository.getRestaurants(query)
            _restaurantItems.value =
                result.items.asUiModel(result.influencerName, result.influencerType, result.influencerImageUrl)
        }
    }

}