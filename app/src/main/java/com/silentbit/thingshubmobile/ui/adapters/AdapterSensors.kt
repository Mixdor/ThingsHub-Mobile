package com.silentbit.thingshubmobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.databinding.CardSensorBinding
import com.silentbit.thingshubmobile.databinding.FragSensorsBinding
import com.silentbit.thingshubmobile.domain.objs.ObjSensor

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
            id.text = sensorItem.id
            name.text = sensorItem.name
            value.text = sensorItem.value.toString()
            isPercentage.isVisible = sensorItem.isPercentage
            idDevice.text = sensorItem.idDevice

            idDevice.setOnClickListener {
                val context = idDevice.context
                Toast.makeText(
                    context,
                    sensorItem.nameDevice,
                    Toast.LENGTH_SHORT
                ).show()
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
