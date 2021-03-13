package com.fooin.android.model

import android.os.Parcelable
import com.fooin.android.model.enums.InfluencerType
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiRestaurantModel(
    val restaurantId: Int,
    val restaurantName: String,
    val influencerType: InfluencerType,
    val influencerName: String,
    val influencerImageUrl: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val link: String,
) : Parcelable