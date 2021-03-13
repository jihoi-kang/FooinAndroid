package com.fooin.android.data.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("positions")
    val positions: List<Position>,
    @SerializedName("name")
    val name: String
)