package com.example.android1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val apiKey = "49967e78a3bbea2f57da1ee8820da348"
    private val units = "metric"

    private val weatherRepository = WeatherRepository()

    private val _currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeather: LiveData<WeatherResponse>
        get() = _currentWeather

    fun getCurrentWeather(city: String) {
        weatherRepository.getCurrentWeather(city, apiKey, units)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        _currentWeather.value = response.body()
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    // Handle errors here
                }
            })
    }

}
