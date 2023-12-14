package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseBackend
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewDeviceCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseBackend: FirebaseBackend
){

    suspend operator fun invoke(name: String, description: String, identification: String) : Boolean{
        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseBackend.newDevice(name, description, identification)
            else -> {false}
        }
    }

}