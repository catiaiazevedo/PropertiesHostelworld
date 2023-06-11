package com.example.propertieshostelworld.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://gist.githubusercontent.com"

class RetrofitClient {
    private fun getRetrofitInstance(): Retrofit {
        val httpclient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpclient.addInterceptor { chain: Interceptor.Chain ->
            val request: Request = chain.request().newBuilder()
                .build()
            chain.proceed(request)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpclient.build())
            .build()
    }

    fun getService(): ApiService {
        return getRetrofitInstance().create(ApiService::class.java)
    }
}
