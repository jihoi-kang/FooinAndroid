package com.fooin.android.data

import com.fooin.android.data.response.GetRestaurantsResponse

interface RestaurantRepository {
    suspend fun getRestaurants(influencerName: String): GetRestaurantsResponse.Result
}