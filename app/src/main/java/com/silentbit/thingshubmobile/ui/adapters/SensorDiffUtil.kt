package com.silentbit.thingshubmobile.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.silentbit.thingshubmobile.domain.objs.ObjSensor

class SensorDiffUtil(
    private val oldList: List<ObjSensor>,
    private val newList: List<ObjSensor>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}