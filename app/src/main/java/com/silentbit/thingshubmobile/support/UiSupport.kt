package com.silentbit.thingshubmobile.support

import android.app.Activity
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.silentbit.thingshubmobile.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UiSupport @Inject constructor(
    @ApplicationContext val context: Context,
    private val spanBuilder : SpanBuilder
){

    fun showErrorAlertDialog(activity:Activity, title: String, message: String) {
        MaterialAlertDialogBuilder(activity, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
            .setIcon(R.drawable.baseline_warning_amber_24)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showHelpAlertDialog(activity: Activity, title:String, message: String, extraLayout:Int){

        val listBold = listOf("current_key", "google-services.json", "mobilesdk_app_id")
        val spanMessage = spanBuilder.getSpannableBold(message, listBold)

        MaterialAlertDialogBuilder(activity, com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
            .setIcon(R.drawable.baseline_help_outline_24)
            .setTitle(title)
            .setMessage(spanMessage)
            .setView(extraLayout)
            .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}