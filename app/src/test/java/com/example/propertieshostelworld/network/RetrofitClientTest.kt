package com.example.propertieshostelworld.network

import org.junit.Assert.*
import org.junit.Test
import retrofit2.create

class RetrofitClientTest {
    //Get an instance of Retrofit
    private val instance = RetrofitClient().getRetrofitInstance()
    private val apiService: ApiService = instance.create()

    @Test
    fun getRetrofitInstance() {
        //Assert that, Retrofit's base url matches to our BASE_URL
        assertEquals(instance.baseUrl().url().toString(), BASE_URL)
    }

    @Test
    fun getService() {
        val response = apiService.getAllProperties().blockingSingle()

        //Check for error body
        val errorBody = response.errorBody()
        assertEquals(errorBody, null)

        //Check for success body
        val responseWrapper = response.body()
        assertNotEquals(responseWrapper, null)
        assertEquals(response.code(), 200)
    }
}
