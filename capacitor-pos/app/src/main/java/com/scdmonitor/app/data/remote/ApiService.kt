package com.scdmonitor.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API service for external services such as weather.
 */
interface ApiService {
    @GET("/weather/nearby")
    suspend fun getLocalWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): WeatherResponse
}

data class WeatherResponse(
    val temperatureC: Double,
    val humidity: Double,
    val precipProbability: Double,
    val conditions: String
)
