package com.silentbit.sensehubmobile.domain

import android.content.Context
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseDevice
import com.silentbit.sensehubmobile.domain.objs.ObjDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetDevicesCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseDevice: FirebaseDevice
){
    suspend operator fun invoke(): List<ObjDevice> {

        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseDevice.getDevices()
            else -> {emptyList()}
        }

    }
}