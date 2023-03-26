package com.example.android1

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.atan2

class MainActivity : AppCompatActivity(), SensorEventListener {

    // Declare variables
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the TextView
        textView = findViewById(R.id.text_view)

        // Initialize the sensor manager and accelerometer sensor
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()

        // Register the listener for the accelerometer sensor
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()

        // Unregister the listener for the accelerometer sensor
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used in this example
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            // Get the x and y values from the accelerometer sensor
            val x = event.values[0]
            val y = event.values[1]

            // Calculate the angle using the atan2 function and convert to degrees
            val angle = atan2(y.toDouble(), x.toDouble()) * (180 / Math.PI)

            // Display the angle in the TextView
            textView.text = "Angle: ${angle.toInt() - 90}°"
        }
    }

}
