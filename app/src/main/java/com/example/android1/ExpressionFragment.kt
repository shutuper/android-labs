package com.example.android1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class ExpressionFragment : Fragment(R.layout.expression_fragment) {

    companion object {
        const val FILE_NAME = "added_file"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okButton = view.findViewById<Button>(R.id.OkButton)
        val storageButton = view.findViewById<Button>(R.id.openButton)
        val expressionSelector = view.findViewById<RadioGroup>(R.id.Expressions)

        storageButton.setOnClickListener { startActivity(Intent(context, FileActivity::class.java)) }
        okButton.setOnClickListener(onOkButtonClick(view, expressionSelector))
    }

    private fun onOkButtonClick(view: View, expressionSelector: RadioGroup): (View) ->
    Unit = {
        val expressionChoice = view.findViewById<RadioButton>(expressionSelector.checkedRadioButtonId)

        if (expressionChoice == null)
            AlertDialog.Builder(activity)
                .setMessage("Chose any option before clicking 'Ok' button")
                .setCancelable(true)
                .show()
        else parentFragmentManager.commit {
            val data = Bundle()
            val result = "Result: ${if (expressionChoice.id == R.id.twoPlusTwo) "4" else "0"}"
            data.putString("result", result)

            val resultFragment = ResultFragment()
            resultFragment.arguments = data

            try {

                context?.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                    it?.write(result.toByteArray())
                    Toast.makeText(context, "Result has been written to file", Toast.LENGTH_SHORT).show()
                }

                replace(R.id.fg, resultFragment)

                addToBackStack(null)
            } catch (e: Throwable) {
                Log.e("Error", e.message!!)
                Toast.makeText(context, "Writing to file failed !!!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}