package com.fooin.android.data.prepackage


import com.fooin.android.model.Restaurant
import com.google.gson.annotations.SerializedName

data class PrePackagedResponse(
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
        @SerializedName("title")
        val title: String
    )
}