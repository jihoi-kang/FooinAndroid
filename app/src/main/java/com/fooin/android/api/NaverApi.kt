package com.fooin.android.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {

    @GET("/map-geocode/v2/geocode")
    suspend fun getGeoCode(
        @Query("query") query: String,
    ): GetGeoCodeResponse

}