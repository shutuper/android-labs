package com.example.android1

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class ExpressionFragment : Fragment(R.layout.expression_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okButton = view.findViewById<Button>(R.id.OkButton)
        val expressionSelector = view.findViewById<RadioGroup>(R.id.Expressions)

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
            data.putString("result", if (expressionChoice.id == R.id.twoPlusTwo) "4" else "0")
            val resultFragment = ResultFragment()
            resultFragment.arguments = data
            replace(R.id.fg, resultFragment)

            addToBackStack(null)
        }

    }

}