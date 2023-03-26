package com.example.android1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val apiService: WeatherApiService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)

    fun getCurrentWeather(city: String, apiKey: String, units: String) =
        apiService.getCurrentWeather(city, apiKey, units)

}
