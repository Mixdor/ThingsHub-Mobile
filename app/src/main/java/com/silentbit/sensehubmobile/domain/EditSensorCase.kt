package com.silentbit.sensehubmobile.domain

import android.content.Context
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseSensor
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
    ) : Boolean{

        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseSensor.editDevice(
                idSensor,
                nameSensor,
                magnitude,
                isPercentage,
            )
            else -> {false}
        }
    }

}