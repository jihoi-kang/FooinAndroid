package com.fooin.android.data

import com.fooin.android.data.prepackage.PrePackagedDb
import com.fooin.android.model.Restaurant
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class RestaurantRepositoryImpl @Inject constructor(
    private val prePackagedDb: PrePackagedDb,
) : RestaurantRepository {

    override suspend fun getRestaurants(youtuberName: String): List<Restaurant> {
        return prePackagedDb.getRestaurants(youtuberName)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RestaurantRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(restaurantRepositoryImpl: RestaurantRepositoryImpl): RestaurantRepository

}