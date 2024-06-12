package com.silentbit.thingshubmobile.support

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.databinding.DialogRangeBinding
import javax.inject.Inject

class CustomDialogs @Inject constructor() {

    private lateinit var context : Context
    private var characterMin = ""
    private var characterMax = ""

    fun init(context: Context){
        this.context = context
    }

    fun showDialogRange(chip: Chip) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bind : DialogRangeBinding = DialogRangeBinding.inflate(inflater)

        val dialog = MaterialAlertDialogBuilder(context)
            .setView(bind.root)
            .setCancelable(false)
            .setNegativeButton("Cancelar"){ dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Aceptar"){ dialog, _ ->
                val stringRange = "$characterMin${bind.txtrangeRegularLeft.text}, ${bind.txtrangeRegularRight.text}$characterMax"
                chip.text = stringRange
                dialog.dismiss()
            }
            .show()

        val buttonPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        buttonPositive.isEnabled = false

        val strChip = chip.text.toString()

        val strLeft = extractMin(strChip, bind.btnLimitMin)
        if (strLeft.contains("∞")) bind.txtrangeRegularLeft.isEnabled = false
        bind.txtrangeRegularLeft.setText(strLeft)
        bind.txtrangeRegularLeft.setSelection(strLeft.length)

        val strRight = extractMax(strChip, bind.btnLimitMax)
        if (strRight.contains("∞")) bind.txtrangeRegularRight.isEnabled = false
        bind.txtrangeRegularRight.setText(strRight)

        bind.btnRangeHelp.setOnClickListener {
            bind.rangeLayoutHelp.isVisible = !bind.rangeLayoutHelp.isVisible

            if (bind.dividerRangeHelp.visibility == View.INVISIBLE){
                bind.dividerRangeHelp.visibility = View.VISIBLE
            }
            else{
                bind.dividerRangeHelp.visibility = View.INVISIBLE
            }
        }

        bind.rangeRegularLeft.isEndIconVisible = false
        bind.rangeRegularRight.isEndIconVisible = false

        var statusLimitMin = 1
        bind.btnLimitMin.setOnClickListener {
            when(statusLimitMin) {
                0 -> {
                    bind.btnLimitMin.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.twotone_circle_24))
                    bind.txtrangeRegularLeft.setText("")
                    bind.txtrangeRegularLeft.isEnabled = true
                    characterMin = "("
                    statusLimitMin += 1
                }
                1 -> {
                    bind.btnLimitMin.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_circle_24))
                    bind.txtrangeRegularLeft.isEnabled = true
                    if (bind.txtrangeRegularLeft.text.toString() == "-∞") bind.txtrangeRegularLeft.setText("")
                    characterMin = "["
                    statusLimitMin += 1
                }
                2 -> {
                    bind.btnLimitMin.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.twotone_circle_24))
                    bind.txtrangeRegularLeft.setText("-∞")
                    bind.txtrangeRegularLeft.isEnabled = false
                    characterMin = "("
                    statusLimitMin = 0
                }
            }
        }

        var statusLimitMax = 1
        bind.btnLimitMax.setOnClickListener {
            when(statusLimitMax) {
                0 -> {
                    bind.btnLimitMax.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.twotone_circle_24))
                    bind.txtrangeRegularRight.setText("")
                    bind.txtrangeRegularRight.isEnabled = true
                    characterMax = ")"
                    statusLimitMax += 1
                }
                1 -> {
                    bind.btnLimitMax.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_circle_24))
                    bind.txtrangeRegularRight.isEnabled = true
                    if (bind.txtrangeRegularRight.text.toString() == "∞") bind.txtrangeRegularRight.setText("")
                    characterMax = "]"
                    statusLimitMax += 1
                }
                2 -> {
                    bind.btnLimitMax.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.twotone_circle_24))
                    bind.txtrangeRegularRight.setText("∞")
                    bind.txtrangeRegularRight.isEnabled = false
                    characterMax = ")"
                    statusLimitMax = 0
                }
            }
        }

        bind.rangeRegularLeft.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val textChanged = s.toString()
                val textLeft = bind.txtrangeRegularLeft.text.toString()
                val textRight = bind.txtrangeRegularRight.text.toString()

                if (textLeft != "" && textRight != "" && textChanged != "-" && textChanged != "."){
                    if (textRight != "∞" && textLeft != "-∞"){
                        if (textLeft.toDouble() > textRight.toDouble()){
                            toggleError(bind, buttonPositive, true)
                        }
                        else{
                            toggleError(bind, buttonPositive, false)
                        }
                    }
                    else{
                        toggleError(bind, buttonPositive, false)
                    }
                }
                else{
                    toggleError(bind, buttonPositive, true)
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        bind.rangeRegularRight.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val textChanged = s.toString()
                val textLeft = bind.txtrangeRegularLeft.text.toString()
                val textRight = bind.txtrangeRegularRight.text.toString()

                if (textLeft != "" && textRight != "" && textChanged != "-" && textChanged != "."){
                    if (textRight != "∞" && textLeft != "-∞"){
                        if (textLeft.toDouble() > textRight.toDouble()){
                            toggleError(bind, buttonPositive, true)
                        }
                        else{
                            toggleError(bind, buttonPositive, false)
                        }
                    }
                    else{
                        toggleError(bind, buttonPositive, false)
                    }
                }
                else{
                    toggleError(bind, buttonPositive, true)
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })



    }

    private fun toggleError(bind: DialogRangeBinding, buttonPositive:Button, isVisible: Boolean) {

        if (isVisible){
            bind.rangeRegularLeft.error = "Error"
            bind.rangeRegularRight.error = "Error"
        }
        else{
            bind.rangeRegularLeft.isErrorEnabled = false
            bind.rangeRegularRight.isErrorEnabled = false
        }

        buttonPositive.isEnabled = !isVisible
    }

    private fun extractMin(strChip: String, btnLimit:ImageButton): String {
        val regex = Regex("^[(\\[](-|\\d|\\.|∞)+")
        var match = regex.find(strChip)?.value ?: ""

        if (match.contains("(")){
            btnLimit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.twotone_circle_24))
            characterMin = "("
        }
        else{
            btnLimit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_circle_24))
            characterMin = "["
        }

        match = match.replace("(", "")
        match = match.replace("[", "")

        return match
    }

    private fun extractMax(strChip:String, btnLimit:ImageButton): String {
        val regex = Regex("(-|\\d|\\.|∞)+[)\\]]$")
        var match = regex.find(strChip)?.value ?: ""

        if (match.contains(")")){
            btnLimit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.twotone_circle_24))
            characterMax = ")"
        }
        else{
            btnLimit.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_circle_24))
            characterMax = "]"
        }

        match = match.replace(")", "")
        match = match.replace("]", "")

        return match
    }

}