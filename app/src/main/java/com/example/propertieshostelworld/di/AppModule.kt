package com.example.propertieshostelworld.di

import com.example.propertieshostelworld.network.ApiService
import com.example.propertieshostelworld.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // providesBaseUrl() provides for provideRetrofitInstance
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    // provideRetrofitInstance(String) provides for PropertyRepository
    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String) : ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
}
