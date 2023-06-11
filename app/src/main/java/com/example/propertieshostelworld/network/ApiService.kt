package com.example.propertieshostelworld.network

import com.example.propertieshostelworld.model.PropertyResults
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("/ollerandreshw/e02c83a2c973c625bbc250e1d93a2040/raw/55b40d1b4e96fd8cde73aebb8d229a45dff28f2d/properties.json")
    fun getAllProperties(): Observable<PropertyResults>
}
