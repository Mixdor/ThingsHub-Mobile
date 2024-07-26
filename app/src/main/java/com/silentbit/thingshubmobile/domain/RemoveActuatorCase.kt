package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseActuator
import com.silentbit.thingshubmobile.domain.objs.ObjActuator
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