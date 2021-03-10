package com.fooin.android.data.prepackage

import com.fooin.android.model.Restaurant

interface PrePackagedDb {

    suspend fun getRestaurants(youtuberName: String): List<Restaurant>

}