package com.silentbit.thingshubmobile.domain

import android.app.Activity
import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseBackend
import com.silentbit.thingshubmobile.ui.viewmodel.ViewModelDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDevicesListenerCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseBackend: FirebaseBackend
){
    operator fun invoke(activity: Activity, viewModelDevice: ViewModelDevice) {

        CoroutineScope(Dispatchers.IO).launch{
            val typeServer = dataStoreManager.loadTypeServer()
            withContext(Dispatchers.Main){
                when(typeServer){
                    context.getString(R.string.firebase) -> firebaseBackend.getDevicesListener(activity, viewModelDevice)
                }
            }
        }
    }


}