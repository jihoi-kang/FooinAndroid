package com.fooin.android.data.prepackage

import com.fooin.android.data.response.GetRestaurantsResponse

interface PrePackagedDb {

    suspend fun getRestaurants(influencerName: String): GetRestaurantsResponse.Result

}