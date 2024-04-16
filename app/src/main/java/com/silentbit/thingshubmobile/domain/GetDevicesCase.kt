package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseBackend
import com.silentbit.thingshubmobile.domain.objs.ObjDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetDevicesCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseBackend: FirebaseBackend
){
    suspend operator fun invoke(): List<ObjDevice> {

        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseBackend.getDevices()
            else -> {emptyList()}
        }

    }
}