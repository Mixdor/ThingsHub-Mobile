package com.silentbit.sensehubmobile.domain

import android.content.Context
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseDashboard
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SetValueActuatorCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataStoreManager: DataStoreManager,
    private val firebaseDashboard: FirebaseDashboard
) {

    suspend operator fun invoke(idActuator:String, value:Any) {
        when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseDashboard.setValueActuator(idActuator, value)
        }
    }

}