package com.fooin.android.data

import com.fooin.android.model.Restaurant

interface RestaurantRepository {
    suspend fun getRestaurants(youtuberName: String): List<Restaurant>
}