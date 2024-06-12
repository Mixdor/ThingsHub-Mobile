package com.silentbit.thingshubmobile.support

import android.app.Activity
import android.content.Context
import android.widget.AutoCompleteTextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.domain.objs.ObjMagnitude
import com.silentbit.thingshubmobile.ui.adapters.ArrayAdapterMagnitude
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

    fun loadMagnitudes(txtMagnitude: AutoCompleteTextView) : List<ObjMagnitude>{
        val itemsMagnitude = listOf(
            ObjMagnitude(R.drawable.baseline_air_24,"Fluido"),
            ObjMagnitude(R.drawable.outline_device_thermostat_24, "Temperatura"),
            ObjMagnitude(R.drawable.outline_humidity_mid_24, "Humedad"),
            ObjMagnitude(R.drawable.outline_infrared_24, "Infrarojo"),
            ObjMagnitude(R.drawable.rounded_altitude_24, "Altitud"),
            ObjMagnitude(R.drawable.outline_water_ph_24, "PH (Acidez)"),
            ObjMagnitude(R.drawable.outline_weight_24, "Masa"),
            ObjMagnitude(R.drawable.baseline_volume_up_24, "Sonido"),
            ObjMagnitude(R.drawable.baseline_sunny_24, "Iluminación"),
            ObjMagnitude(R.drawable.baseline_palette_24, "Color"),
            ObjMagnitude(R.drawable.baseline_access_time_24, "Tiempo"),
            ObjMagnitude(R.drawable.baseline_compress_24, "Compresión"),
            ObjMagnitude(R.drawable.outline_expand_24, "Expansión"),
            ObjMagnitude(R.drawable.baseline_electric_bolt_24, "Electricidad"),
            ObjMagnitude(R.drawable.baseline_my_location_24, "Ubicación"),
            ObjMagnitude(R.drawable.baseline_radar_24, "Radar"),
            ObjMagnitude(R.drawable.baseline_touch_app_24, "Tactil"),
            ObjMagnitude(R.drawable.baseline_visibility_24, "Presencia"),
            ObjMagnitude(R.drawable.outline_speed_24, "Velocidad"),
            ObjMagnitude(R.drawable.outline_height_24, "Altura"),
            ObjMagnitude(R.drawable.outline_width_24, "Distancia"),
            ObjMagnitude(R.drawable.outline_total_dissolved_solids_24, "Sustancia"),
            ObjMagnitude(R.drawable.baseline_cyclone_24, "Giroscopio"),
            ObjMagnitude(R.drawable.outline_detector_smoke_24, "Humo"),
            ObjMagnitude(R.drawable.outline_water_medium_24, "Nivel"),
        )
        val adapterMagnitude = ArrayAdapterMagnitude(context, itemsMagnitude)
        (txtMagnitude as? AutoCompleteTextView)?.setAdapter(adapterMagnitude)

        return itemsMagnitude
    }

}