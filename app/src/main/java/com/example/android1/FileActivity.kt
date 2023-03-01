package com.example.android1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.android1.ExpressionFragment.Companion.FILE_NAME

class FileActivity : AppCompatActivity() {

    private val emptyFile = "File is empty !!!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_file)

        val textField = findViewById<TextView>(R.id.fileText)

        textField.text =
            try {
                this.openFileInput(FILE_NAME).bufferedReader().useLines { lines ->
                    lines.fold("") { some, text ->
                        "$some\n$text"
                    }
                }
            } catch (e: Throwable) {
                Toast.makeText(applicationContext, emptyFile, Toast.LENGTH_SHORT).show()
                emptyFile
            }
    }

}