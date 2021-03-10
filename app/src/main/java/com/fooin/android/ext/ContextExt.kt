package com.fooin.android.ext

import android.content.Context
import com.google.gson.Gson

inline fun <reified ENTITY> Context.loadJson(gson: Gson, fileName: String): ENTITY {
    val result = assets.open(fileName)
        .bufferedReader()
        .use { it.readText() }

    return gson.fromJson(result, ENTITY::class.java)
}