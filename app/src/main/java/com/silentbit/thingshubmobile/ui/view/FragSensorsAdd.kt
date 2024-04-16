package com.silentbit.thingshubmobile.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.databinding.FragSensorsAddBinding
import com.silentbit.thingshubmobile.domain.objs.ObjMagnitude
import com.silentbit.thingshubmobile.ui.adapters.ArrayAdapterMagnitude
import com.silentbit.thingshubmobile.ui.viewmodel.ViewModelSensorAdd
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragSensorsAdd : Fragment() {

    private val viewModelSensorAdd : ViewModelSensorAdd by viewModels()
    private var _binding : FragSensorsAddBinding? = null
    private val binding get() = _binding!!
    private var magnitude = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSensorsAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val adapterMagnitude = ArrayAdapterMagnitude(requireContext(), itemsMagnitude)
        (binding.txtMagnitude as? AutoCompleteTextView)?.setAdapter(adapterMagnitude)


        binding.txtMagnitude.setOnItemClickListener { adapterView, viewAdpt, position, l ->

            magnitude = itemsMagnitude[position].idImage
            binding.magnitudeLayout.startIconDrawable = ContextCompat.getDrawable(
                requireContext(),
                magnitude
            )
        }


        viewModelSensorAdd.getDevices()

        viewModelSensorAdd.devices.observe(requireActivity()) { listDevices ->

            val arrayDevice = listDevices.map {
                "#${it.id} - ${it.name}"
            }.toTypedArray()
            (binding.txtDevice as? MaterialAutoCompleteTextView)?.setSimpleItems(arrayDevice)
        }

        var statusRegular = 0
        statusRegular = changeRangeRegular(statusRegular, binding.btnRangeRegular, binding.rangeRegularLeft, binding.rangeRegularRight)

        binding.btnRangeRegular.setOnClickListener {
            statusRegular = changeRangeRegular(statusRegular, binding.btnRangeRegular, binding.rangeRegularLeft, binding.rangeRegularRight)
        }

        var statusWarning = 0
        statusWarning = changeRangeRegular(statusWarning, binding.btnRangeWarning, binding.rangeWarningLeft, binding.rangeWarningRight)

        binding.btnRangeWarning.setOnClickListener {
            statusWarning = changeRangeRegular(statusWarning, binding.btnRangeWarning, binding.rangeWarningLeft, binding.rangeWarningRight)
        }

        var statusCritical = 0
        statusCritical = changeRangeRegular(statusCritical, binding.btnRangeCritical, binding.rangeCriticalLeft, binding.rangeCriticalRight)

        binding.btnRangeCritical.setOnClickListener {
            statusCritical = changeRangeRegular(statusCritical, binding.btnRangeCritical, binding.rangeCriticalLeft, binding.rangeCriticalRight)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("idMagnitude", magnitude)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val idResourceMagnitude = savedInstanceState?.getInt("idMagnitude") ?: 0
        if (idResourceMagnitude!=0){
            binding.magnitudeLayout.startIconDrawable = ContextCompat.getDrawable(
                requireContext(),
                idResourceMagnitude
            )
            magnitude = idResourceMagnitude
        }
    }

    private fun changeRangeRegular(
        status : Int,
        button : Button,
        leftLayout : TextInputLayout,
        rightLayout : TextInputLayout
    ): Int {
        Log.e("status", status.toString())
        var statusLocal = status
        leftLayout.visibility = View.VISIBLE
        rightLayout.visibility = View.VISIBLE
        when (status) {
            0 -> button.text = "< x <"
            1 -> button.text = "≤ x <"
            2 -> button.text = "< x ≤"
            3 -> button.text = "≤ x ≤"
            4 -> {
                rightLayout.visibility = View.INVISIBLE
                button.text = "< x  "
            }

            5 -> {
                rightLayout.visibility = View.INVISIBLE
                button.text = "≤ x  "
            }

            6 -> {
                leftLayout.visibility = View.INVISIBLE
                button.text = "  x <"
            }

            7 -> {
                statusLocal = -1
                leftLayout.visibility = View.INVISIBLE
                button.text = "  x ≤"
            }
        }
        binding.dummyLayout.requestFocus()
        return statusLocal + 1
    }

}