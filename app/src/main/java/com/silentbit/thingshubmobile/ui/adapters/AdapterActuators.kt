package com.silentbit.thingshubmobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.databinding.CardActuatorBinding
import com.silentbit.thingshubmobile.databinding.FragActuatorsBinding
import com.silentbit.thingshubmobile.domain.objs.ObjActuator

class AdapterActuators(
    var data : List<ObjActuator>,
    val fragBinding : FragActuatorsBinding
) : RecyclerView.Adapter<AdapterActuators.ActuatorViewHolder>() {

    var dataChecked : List<ObjActuator> = listOf()

    class ActuatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardActuatorBinding.bind(view)
        var card = binding.cardActuatorRoot
        var id = binding.textIdActuator
        var name = binding.textNameActuator
        var idDevice = binding.cardActuatorDevice

        fun render(actuatorItem: ObjActuator) {

            val context = idDevice.context
            actuatorItem.id.also { id.text = it }
            name.text = actuatorItem.name
            idDevice.text = actuatorItem.idDevice

            idDevice.setOnClickListener {

                Toast.makeText(
                    context,
                    actuatorItem.nameDevice,
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActuatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_actuator, parent, false)
        return ActuatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActuatorViewHolder, position: Int) {

        val actuatorItem = data[position]
        holder.render(actuatorItem)

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
                dataChecked.plus(actuatorItem)
            } else{
                dataChecked.minus(actuatorItem)
            }
            if (getItemsCheckedCount()>0){
                fragBinding.fabActuatorEdit.visibility = View.VISIBLE
                fragBinding.fabActuatorRemove.visibility = View.VISIBLE
                if (getItemsCheckedCount()>1){
                    fragBinding.fabActuatorEdit.visibility = View.GONE
                }
            }
            else{
                fragBinding.fabActuatorEdit.visibility = View.GONE
                fragBinding.fabActuatorRemove.visibility = View.GONE
            }
        }
    }

    fun updateData(newData: List<ObjActuator>):Boolean{
        val diffUtil = ActuatorDiffUtil(data, newData)
        val calculate = DiffUtil.calculateDiff(diffUtil)
        data = newData
        calculate.dispatchUpdatesTo(this)
        return true
    }

    override fun getItemCount(): Int = data.size

    private fun getItemsCheckedCount(): Int = dataChecked.size

}