package com.silentbit.sensehubmobile.domain

import android.content.Context
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseActuator
import com.silentbit.sensehubmobile.domain.objs.ObjActuator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoveActuatorCase @Inject constructor(
    @ApplicationContext private val context : Context,
    private val dataStoreManager : DataStoreManager,
    private val firebaseActuator : FirebaseActuator
){

    suspend operator fun invoke(listRemove:List<ObjActuator>, oldList: List<ObjActuator>) : List<ObjActuator>{
        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseActuator.removeActuators(listRemove, oldList)
            else -> emptyList()
        }
    }

}