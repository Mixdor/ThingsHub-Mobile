package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseSensor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EditSensorCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataStoreManager: DataStoreManager,
    private val firebaseSensor: FirebaseSensor
) {

    suspend operator fun invoke(
        idSensor: String,
        nameSensor: String,
        magnitude: Int,
        isPercentage: Boolean,
        isEnableRanges: Boolean,
        ranges: String
    ) : Boolean{

        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseSensor.editDevice(
                idSensor,
                nameSensor,
                magnitude,
                isPercentage,
                isEnableRanges,
                ranges
            )
            else -> {false}
        }
    }

}