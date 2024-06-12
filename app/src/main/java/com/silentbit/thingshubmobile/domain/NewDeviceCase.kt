package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewDeviceCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseDevice: FirebaseDevice
){

    suspend operator fun invoke(name: String, description: String, identification: String) : Boolean{
        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseDevice.newDevice(name, description, identification)
            else -> {false}
        }
    }

}