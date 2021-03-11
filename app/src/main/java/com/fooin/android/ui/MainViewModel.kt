package com.fooin.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fooin.android.base.BaseViewModel
import com.fooin.android.data.RestaurantRepository
import com.fooin.android.model.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : BaseViewModel() {

    val query = MutableLiveData<String>()

    private val _restaurantItems = MutableLiveData<List<Restaurant>>(emptyList())
    val restaurantItems: LiveData<List<Restaurant>> get() = _restaurantItems

    private val _hideKeyboardEvent = MutableLiveData<Unit>()
    val hideKeyboardEvent: LiveData<Unit> get() = _hideKeyboardEvent

    fun getRestaurants() {
        val query = query.value ?: return

        viewModelScope.launch {
            _hideKeyboardEvent.value = Unit
            val restaurants = repository.getRestaurants(query)
            _restaurantItems.value = restaurants
        }
    }

}