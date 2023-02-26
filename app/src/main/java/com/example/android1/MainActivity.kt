package com.example.android1

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val expressionSelector = findViewById<RadioGroup>(R.id.Expressions)
        val okButton = findViewById<Button>(R.id.OkButton)
        val resultOutput = findViewById<TextView>(R.id.ResultOutput)

        okButton.setOnClickListener(onOkClick(expressionSelector, resultOutput))
    }

    private fun onOkClick(expressionSelector: RadioGroup, textOutput: TextView): (View) -> Unit {
        val function: (View) -> Unit = {
            val expressionChoice = findViewById<RadioButton>(expressionSelector.checkedRadioButtonId)

            if (expressionChoice == null) {
                AlertDialog.Builder(this)
                    .setMessage("Chose any option before clicking 'Ok' button")
                    .setCancelable(true)
                    .show()
            } else {
                val text = if (expressionChoice.id == R.id.twoPlusTwo) "4" else "0"
                textOutput.text = "Result: ${text}"
            }
        }
        return function
    }

}