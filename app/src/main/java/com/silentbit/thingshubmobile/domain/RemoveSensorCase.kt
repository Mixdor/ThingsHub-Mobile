package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseSensor
import com.silentbit.thingshubmobile.domain.objs.ObjSensor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoveSensorCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager: DataStoreManager,
    private val firebaseSensor: FirebaseSensor
) {
    suspend operator fun invoke(listRemove:List<ObjSensor>, oldList: List<ObjSensor>) : List<ObjSensor>{
        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseSensor.removeSensor(listRemove, oldList)
            else -> emptyList()
        }
    }
}