package com.silentbit.sensehubmobile.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.databinding.CardDashboardActuatorBinding
import com.silentbit.sensehubmobile.databinding.CardDashboardSensorBinding
import com.silentbit.sensehubmobile.domain.objs.ObjActuator
import com.silentbit.sensehubmobile.domain.objs.ObjSensor
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelDashboard

class AdapterDashboard(
    var data : List<Any>,
    val viewModelDashboard: ViewModelDashboard
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val typeSensor = 1
    private val typeActuator = 0

    class EmptyViewHolder(view:View) : RecyclerView.ViewHolder(view)

    class DashboardSensorViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val binding = CardDashboardSensorBinding.bind(view)
        private val name = binding.cardName
        private val value = binding.cardSensorValue
        private val percentage = binding.cardSensorPercentage
        private val magnitude = binding.cardSensorMagnitude

        fun render(senorItem:ObjSensor){

            val context = name.context

            name.text = senorItem.name
            value.text = senorItem.value.toString()
            percentage.isVisible = senorItem.isPercentage

            when(senorItem.magnitude.toInt()){
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
                else -> {
                    Log.e("Sensor", "No found icon")}
            }
        }
    }

    class DashboardActuatorViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val binding = CardDashboardActuatorBinding.bind(view)
        private val name = binding.cardName
        val toggle = binding.btnCardActuator

        fun render(objActuator: ObjActuator){

            val context = name.context
            name.text = objActuator.name

            when(objActuator.type){
                1 ->{
                    toggle.isVisible = true
                    if (objActuator.value is Boolean){
                        if (objActuator.value) toggle.setIconTintResource(R.color.primary)
                        else toggle.setIconTintResource(R.color.image_tint)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            typeSensor -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_dashboard_sensor, parent, false)
                DashboardSensorViewHolder(view)
            }
            typeActuator -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_dashboard_actuator, parent, false)
                DashboardActuatorViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(parent.context).inflate(
                    R.layout.empty_layout, parent, false)
                EmptyViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(getItemViewType(position)){
            typeSensor -> {
                val sensorHolder: DashboardSensorViewHolder = holder as DashboardSensorViewHolder
                val dataCurrent = data[position] as ObjSensor
                sensorHolder.render(dataCurrent)
            }
            typeActuator -> {
                val actuatorHolder: DashboardActuatorViewHolder = holder as DashboardActuatorViewHolder
                val dataCurrent = data[position] as ObjActuator

                actuatorHolder.render(dataCurrent)

                actuatorHolder.toggle.setOnClickListener {
                    if (dataCurrent.value is Boolean){
                        viewModelDashboard.setValueActuator(dataCurrent.id, !dataCurrent.value)
                    }
                }

            }
        }

    }

    fun updateData(newData: List<Any>):Boolean{
        val diffUtil = ItemsDashboardDiffUtil(data, newData)
        val calculate = DiffUtil.calculateDiff(diffUtil)
        data = newData
        calculate.dispatchUpdatesTo(this)
        return true
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {

        val item = data[position]

        return when(item){
            is ObjSensor -> typeSensor
            is ObjActuator -> typeActuator
            else -> {0}
        }

    }


}