package com.example.android1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment


class ResultFragment : Fragment(R.layout.result_fragment) {

    private var result = String()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textBox = view.findViewById<TextView>(R.id.textView)

        textBox.text = result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments

        if (bundle != null) {
            result = "Result: ${bundle.getString("result")!!}"
        }
    }

}