package com.scdmonitor.app.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Provides Retrofit instance and API service. Replace baseUrl with your real endpoint.
 */
object NetworkModule {
    private const val BASE_URL = "https://api.example.com"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
