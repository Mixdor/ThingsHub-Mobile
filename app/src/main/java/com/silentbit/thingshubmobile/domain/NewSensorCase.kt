package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseSensor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewSensorCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseSensor: FirebaseSensor
) {

    suspend operator fun invoke(idSensor:String, nameSensor:String, typeSensor:Int, idDevice:String, isPercentage:Boolean, enableRanges:Boolean, ranges:String) : Boolean{
        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseSensor.newSensor(
                idSensor = idSensor,
                nameSensor = nameSensor,
                typeSensor = typeSensor,
                idDevice = idDevice,
                isPercentage = isPercentage,
                enableRanges = enableRanges,
                ranges = ranges
            )
            else -> {false}
        }
    }

}