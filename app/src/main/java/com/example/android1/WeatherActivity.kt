package com.example.android1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels


class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var textViewCity: TextView
    private lateinit var textViewWeather: TextView
    private lateinit var textViewTemperature: TextView
    private lateinit var textViewFeelsLike: TextView
    private lateinit var textViewMinTemperature: TextView
    private lateinit var textViewMaxTemperature: TextView
    private lateinit var textViewPressure: TextView
    private lateinit var textViewHumidity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        textViewCity = findViewById(R.id.textViewCity)
        textViewWeather = findViewById(R.id.textViewWeather)
        textViewTemperature = findViewById(R.id.textViewTemperature)
        textViewFeelsLike = findViewById(R.id.textViewFeelsLike)
        textViewMinTemperature = findViewById(R.id.textViewMinTemperature)
        textViewMaxTemperature = findViewById(R.id.textViewMaxTemperature)
        textViewPressure = findViewById(R.id.textViewPressure)
        textViewHumidity = findViewById(R.id.textViewHumidity)

        button.setOnClickListener {
            val city = editText.text.toString().trim()
            if (city.isNotBlank()) viewModel.getCurrentWeather(city)
        }

        viewModel.currentWeather.observe(this) {
            val weather = it.weather[0]
            val main = it.main

            textViewCity.text = it.name
            textViewWeather.text = weather.main
            textViewTemperature.text = "${main.temp} °C"
            textViewFeelsLike.text = "Feels like ${main.feels_like} °C"
            textViewMinTemperature.text = "Min: ${main.temp_min} °C"
            textViewMaxTemperature.text = "Max: ${main.temp_max} °C"
            textViewPressure.text = "Pressure: ${main.pressure} hPa"
            textViewHumidity.text = "Humidity: ${main.humidity} %"
        }
    }

}
