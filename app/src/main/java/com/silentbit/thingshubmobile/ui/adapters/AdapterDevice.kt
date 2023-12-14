package com.silentbit.thingshubmobile.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.databinding.CardDeviceBinding
import com.silentbit.thingshubmobile.databinding.FragDeviceBinding
import com.silentbit.thingshubmobile.domain.objs.ObjDevice

class AdapterDevice(
    var data : List<ObjDevice>,
    private val fragBinding : FragDeviceBinding
) : RecyclerView.Adapter<AdapterDevice.DeviceViewHolder>() {

    var dataChecked : List<ObjDevice> = listOf()

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardDeviceBinding.bind(view)
        var card = binding.cardDeviceRoot
        var id = binding.cardDeviceId
        var title = binding.cardDeviceTitle
        var description = binding.cardDeviceDescription
        var sensors = binding.cardDeviceSensors
        var actuators = binding.cardDeviceActuators

        fun render(deviceItem:ObjDevice){

            title.text = deviceItem.name
            id.text = StringBuilder("#${deviceItem.id}")
            if (deviceItem.description == "") description.visibility = View.GONE
            else description.text = deviceItem.description
            sensors.text = deviceItem.sensors.toString()
            actuators.text = deviceItem.actuators.toString()

            sensors.setOnClickListener {
                val context = sensors.context
                Toast.makeText(
                    context,
                    context.getString(R.string.sensors)+": ${sensors.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            actuators.setOnClickListener {
                val context = actuators.context
                Toast.makeText(
                    context,
                    context.getString(R.string.actuators)+": ${actuators.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    fun updateData(newData: List<ObjDevice>):Boolean{
        val diffUtil = DeviceDiffUtil(data,newData)
        val calcule = DiffUtil.calculateDiff(diffUtil)
        data = newData
        calcule.dispatchUpdatesTo(this)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_device, parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val deviceItem = data[position]
        holder.render(deviceItem)

        holder.card.setOnLongClickListener {
            holder.card.isChecked = !holder.card.isChecked
            true
        }

        holder.card.setOnClickListener {
            if (getItemsCheckedCount()>0){
                holder.card.isChecked = !holder.card.isChecked
            }
        }

        holder.card.setOnCheckedChangeListener { _, isChecked ->
            dataChecked = if (isChecked){
                dataChecked.plus(deviceItem)
            } else{
                dataChecked.minus(deviceItem)
            }
            if (getItemsCheckedCount()>0){
                fragBinding.fabDeviceEdit.visibility = View.VISIBLE
                fragBinding.fabDeviceRemove.visibility = View.VISIBLE
                if (getItemsCheckedCount()>1){
                    fragBinding.fabDeviceEdit.visibility = View.GONE
                }
            }
            else{
                fragBinding.fabDeviceEdit.visibility = View.GONE
                fragBinding.fabDeviceRemove.visibility = View.GONE
            }
            Log.e("Checkeds", dataChecked.toString())
        }
    }

    override fun getItemCount(): Int = data.size

    private fun getItemsCheckedCount(): Int = dataChecked.size

}
