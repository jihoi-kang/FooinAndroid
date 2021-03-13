package com.fooin.android.data.response

import com.fooin.android.data.model.Restaurant
import com.google.gson.annotations.SerializedName

data class GetRestaurantsResponse(
    @SerializedName("result")
    val result: Result,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String
) {
    data class Result(
        @SerializedName("items")
        val items: List<Restaurant>,
        @SerializedName("link")
        val link: String,
        @SerializedName("influencerType")
        val influencerType: String,
        @SerializedName("influencerName")
        val influencerName: String,
        @SerializedName("influencerImageUrl")
        val influencerImageUrl: String,
    )
}