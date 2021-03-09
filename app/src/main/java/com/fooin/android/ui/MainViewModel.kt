package com.fooin.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fooin.android.api.GetGeoCodeResponse
import com.fooin.android.api.NaverApi
import com.fooin.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val naverApi: NaverApi
) : BaseViewModel() {

    val query = MutableLiveData<String>()

    private val _markGeoEvent = MutableLiveData<GetGeoCodeResponse.Addresse>()
    val markGeoEvent: LiveData<GetGeoCodeResponse.Addresse> get() = _markGeoEvent

    fun getGeoCode() {
        val query = query.value ?: return

        viewModelScope.launch {
            val response = naverApi.getGeoCode(query)
            if (response.addresses.isNotEmpty()) {
                _markGeoEvent.value = response.addresses[0]
            }
        }
    }

}