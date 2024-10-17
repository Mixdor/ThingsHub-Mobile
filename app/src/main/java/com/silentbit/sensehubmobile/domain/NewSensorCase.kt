package com.silentbit.sensehubmobile.domain

import android.content.Context
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseSensor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewSensorCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseSensor: FirebaseSensor
) {

    suspend operator fun invoke(idSensor:String, nameSensor:String, typeSensor:Int, idDevice:String, isPercentage:Boolean) : Boolean{
        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseSensor.newSensor(
                idSensor = idSensor,
                nameSensor = nameSensor,
                typeSensor = typeSensor,
                idDevice = idDevice,
                isPercentage = isPercentage,
            )
            else -> {false}
        }
    }

}