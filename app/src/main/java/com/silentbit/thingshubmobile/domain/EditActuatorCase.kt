package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseActuator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EditActuatorCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataStoreManager: DataStoreManager,
    private val firebaseActuator: FirebaseActuator
) {

    suspend operator fun invoke(idActuator : String, nameActuator : String, typeActuator : Int) : Boolean{

        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseActuator.editActuator(
                idActuator, nameActuator, typeActuator
            )
            else -> {false}
        }


    }

}