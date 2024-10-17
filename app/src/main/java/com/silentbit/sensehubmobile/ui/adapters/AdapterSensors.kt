package com.silentbit.sensehubmobile.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.databinding.CardSensorBinding
import com.silentbit.sensehubmobile.databinding.FragSensorsBinding
import com.silentbit.sensehubmobile.domain.objs.ObjSensor

class AdapterSensors(
    var data: List<ObjSensor>,
    val fragBinding: FragSensorsBinding
) : RecyclerView.Adapter<AdapterSensors.SensorViewHolder>() {

    var dataChecked : List<ObjSensor> = listOf()

    class SensorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardSensorBinding.bind(view)
        var card = binding.cardSensorRoot
        var id = binding.cardSensorId
        var name = binding.cardSensorName
        var value = binding.cardSensorValue
        var magnitude = binding.cardSensorMagnitude
        var isPercentage = binding.cardSensorPercentage
        var idDevice = binding.cardSensorDevice

        fun render(sensorItem:ObjSensor){

            val context = idDevice.context

            sensorItem.id.also { id.text = it }
            name.text = sensorItem.name
            value.text = sensorItem.value.toString()
            isPercentage.isVisible = sensorItem.isPercentage
            idDevice.text = sensorItem.idDevice

            idDevice.setOnClickListener {

                Toast.makeText(
                    context,
                    sensorItem.nameDevice,
                    Toast.LENGTH_SHORT
                ).show()
            }

            when(sensorItem.magnitude.toInt()){
                0 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_air_24))}
                1 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_device_thermostat_24))}
                2 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_humidity_mid_24))}
                3 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_infrared_24))}
                4 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rounded_altitude_24))}
                5 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_water_ph_24))}
                6 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_weight_24))}
                7 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_volume_up_24))}
                8 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_sunny_24))}
                9 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_palette_24))}
                10 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_access_time_24))}
                11 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_compress_24))}
                12 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_expand_24))}
                13 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_electric_bolt_24))}
                14 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_my_location_24))}
                15 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_radar_24))}
                16 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_touch_app_24))}
                17 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_visibility_24))}
                18 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_speed_24))}
                19 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_height_24))}
                20 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_width_24))}
                21 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_total_dissolved_solids_24))}
                22 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_cyclone_24))}
                23 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_detector_smoke_24))}
                24 -> {magnitude.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.outline_water_medium_24))}
                else -> {Log.e("Sensor", "No found icon")}
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_sensor, parent, false)
        return SensorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val sensorItem = data[position]
        holder.render(sensorItem)

        holder.card.setOnLongClickListener {
            holder.card.isChecked = !holder.card.isChecked
            true
        }

        holder.card.setOnClickListener {
            if (getItemsCheckedCount() > 0) {
                holder.card.isChecked = !holder.card.isChecked


            }
        }

        holder.card.setOnCheckedChangeListener { _, isChecked ->
            dataChecked = if (isChecked){
                dataChecked.plus(sensorItem)
            } else{
                dataChecked.minus(sensorItem)
            }
            if (getItemsCheckedCount()>0){
                fragBinding.fabSensorEdit.visibility = View.VISIBLE
                fragBinding.fabSensorRemove.visibility = View.VISIBLE
                if (getItemsCheckedCount()>1){
                    fragBinding.fabSensorEdit.visibility = View.GONE
                }
            }
            else{
                fragBinding.fabSensorEdit.visibility = View.GONE
                fragBinding.fabSensorRemove.visibility = View.GONE
            }

        }

    }

    fun updateData(newData: List<ObjSensor>):Boolean{
        val diffUtil = SensorDiffUtil(data,newData)
        val calculate = DiffUtil.calculateDiff(diffUtil)
        data = newData
        calculate.dispatchUpdatesTo(this)
        return true
    }

    override fun getItemCount(): Int = data.size

    private fun getItemsCheckedCount(): Int = dataChecked.size

}
