package com.silentbit.sensehubmobile.domain

import android.app.Activity
import android.content.Context
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseDashboard
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelDashboard
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSensorsAndActuators @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseDashboard: FirebaseDashboard
) {

    operator fun invoke(activity:Activity, viewModelDashboard: ViewModelDashboard){

        CoroutineScope(Dispatchers.IO).launch{
            val typeServer = dataStoreManager.loadTypeServer()
            withContext(Dispatchers.Main){
                when(typeServer){
                    context.getString(R.string.firebase) -> firebaseDashboard.getSensorsAndActuator(activity, viewModelDashboard)
                }
            }
        }

    }

}