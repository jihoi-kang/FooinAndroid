package com.fooin.android.di

import com.fooin.android.BuildConfig
import com.fooin.android.api.NaverApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val NAVER_BASE_URL = "https://naveropenapi.apigw.ntruss.com/"

    @Provides
    @Singleton
    fun provideNaverApi(retrofit: Retrofit): NaverApi =
        retrofit.create(NaverApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(NAVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("X-NCP-APIGW-API-KEY-ID", BuildConfig.X_NCP_APIGW_API_KEY_ID)
                        .addHeader("X-NCP-APIGW-API-KEY", BuildConfig.X_NCP_APIGW_API_KEY)
                        .build()
                )
            }
            .build()

}