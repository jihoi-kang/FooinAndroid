package com.fooin.android.data.prepackage

import android.content.Context
import com.fooin.android.data.response.GetRestaurantsResponse
import com.fooin.android.ext.loadJson
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class PrePackagedDbImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson,
) : PrePackagedDb {

    override suspend fun getRestaurants(influencerName: String): GetRestaurantsResponse.Result {
        val assetsName = when (influencerName) {
            "김사원세끼" -> "kim.json"
            "먹적" -> "muk.json"
            else -> "error.json"
        }

        return try {
            val response = context.loadJson<GetRestaurantsResponse>(gson, assetsName)
            if (response.status == "ok") {
                response.result
            } else {
                // todo: Need to implement
                throw Exception("Need to implement")
            }
        } catch (e: Exception) {
            throw RuntimeException("json file load error")
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class PrePackagedDbModule {

    @Binds
    @Singleton
    abstract fun bindPrePackagedDb(prePackagedDbImpl: PrePackagedDbImpl): PrePackagedDb

}