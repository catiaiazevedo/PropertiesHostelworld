package com.example.propertieshostelworld.network

import com.example.propertieshostelworld.model.PropertyResults
import com.example.propertieshostelworld.utils.Constants
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.END_POINT)
    fun getAllProperties(): Observable<Response<PropertyResults>>
}
