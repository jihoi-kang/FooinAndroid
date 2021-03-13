package com.fooin.android.model.enums

enum class InfluencerType(type: String) {
    UNKNOWN(""),
    YOUTUBER("youtuber"),
    INSTAGRAM("instagram");

    companion object {
        fun get(gender: String): InfluencerType {
            return when (gender) {
                "youtuber" -> YOUTUBER
                "instagram" -> INSTAGRAM
                else -> UNKNOWN
            }
        }
    }
}