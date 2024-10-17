package com.silentbit.sensehubmobile.domain

import android.app.Activity
import android.content.Context
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseDevice
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDevicesListenerCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseDevice: FirebaseDevice
){
    operator fun invoke(activity: Activity, viewModelDevice: ViewModelDevice) {

        CoroutineScope(Dispatchers.IO).launch{
            val typeServer = dataStoreManager.loadTypeServer()
            withContext(Dispatchers.Main){
                when(typeServer){
                    context.getString(R.string.firebase) -> firebaseDevice.getDevicesListener(activity, viewModelDevice)
                }
            }
        }
    }


}