package com.silentbit.sensehubmobile.domain

import android.content.Context
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseActuator
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