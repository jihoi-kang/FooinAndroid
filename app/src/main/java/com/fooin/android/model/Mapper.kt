package com.fooin.android.model

import com.fooin.android.data.model.Restaurant
import com.fooin.android.model.enums.InfluencerType

fun List<Restaurant>.asUiModel(
    influencerName: String,
    influencerType: String,
    influencerImageUrl: String,
): List<UiRestaurantModel> {
    val uiRestaurants = mutableListOf<UiRestaurantModel>()
    forEach { restaurant ->
        restaurant.positions.forEach { position ->
            uiRestaurants.add(
                UiRestaurantModel(
                    restaurant.id,
                    restaurant.name,
                    InfluencerType.get(influencerType),
                    influencerName,
                    influencerImageUrl,
                    position.address,
                    position.latitude,
                    position.longitude,
                    restaurant.link,
                )
            )
        }
    }
    return uiRestaurants
}