package com.silentbit.sensehubmobile.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.silentbit.sensehubmobile.domain.objs.ObjActuator
import com.silentbit.sensehubmobile.domain.objs.ObjSensor

class ItemsDashboardDiffUtil(
    private val oldList: List<Any>,
    private val newList: List<Any>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        var value = false
        val itemOld = oldList[oldItemPosition]
        val itemNew = newList[newItemPosition]

        if (itemOld is ObjSensor && itemNew is ObjSensor){
            if (itemOld.id == itemNew.id){
                value = true
            }
        }
        else{
            if (itemOld is ObjActuator && itemNew is ObjActuator){
                if (itemOld.id == itemNew.id){
                    value = true
                }
            }
        }

        return value
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        var value = true
        val itemOld = oldList[oldItemPosition]
        val itemNew = newList[newItemPosition]

        if (itemOld is ObjSensor && itemNew is ObjSensor){
            if (itemNew.name != itemOld.name) value = false
            if (itemNew.value != itemOld.value) value = false
            if (itemNew.isPercentage != itemOld.isPercentage) value = false
            if (itemNew.magnitude != itemOld.magnitude) value = false
        }
        else{
            if (itemOld is ObjActuator && itemNew is ObjActuator){
                if (itemNew.name != itemOld.name) value = false
                if (itemNew.type != itemOld.type) value = false
                if (itemNew.value != itemOld.value) value = false
            }
        }

        return value

    }


}