package com.silentbit.thingshubmobile.support

import android.app.Activity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class UiSupport @Inject constructor(){

    fun showErrorAlertDialog(acticity:Activity, title: String, message: String) {
        MaterialAlertDialogBuilder(acticity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}