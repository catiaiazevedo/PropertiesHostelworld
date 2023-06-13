package com.example.propertieshostelworld.repository

import com.example.propertieshostelworld.network.ApiService
import javax.inject.Inject

//api consumes provideRetrofitInstance(String) and PropertyRepository(ApiService) provides for ListViewModel
class PropertyRepository @Inject constructor(private val api: ApiService) {
    fun getAllProperties() = api.getAllProperties()
}
