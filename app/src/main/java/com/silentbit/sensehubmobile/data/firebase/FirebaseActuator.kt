package com.silentbit.sensehubmobile.data.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.domain.objs.ObjActuator
import com.silentbit.sensehubmobile.support.UiSupport
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelActuator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseActuator @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val uiSupport: UiSupport
) {

    fun getActuators(activity: Activity, viewModelActuator: ViewModelActuator) {

        CoroutineScope(Dispatchers.IO).launch {

            val firebaseApp = dataStoreManager.loadFirebaseApp()
            val uid = Firebase.auth(firebaseApp).uid.toString()
            val reference = Firebase.database(firebaseApp).reference.child(uid).child("devices")

            withContext(Dispatchers.Main) {

                reference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value!=null){

                            var dataActuators : List<ObjActuator> = listOf()

                            val devices = snapshot.value as Map<*,*>
                            for(deviceKey in devices.keys) {
                                val device = devices[deviceKey] as Map<*, *>
                                val actuators = device["actuators"] as Map<*, *>?

                                if(actuators!=null){
                                    for(actuatorKey in actuators.keys){
                                        val actuator = actuators[actuatorKey] as Map<*,*>
                                        val name = actuator["name"] as String?

                                        val nameDevice = device["name"] as String?

                                        val tipo = actuator["type"] as Long

                                        val type = tipo.toInt()

                                        var value = actuator["value"]

                                        try {
                                            value = value as Boolean
                                        }catch (e:Exception){
                                            Log.i("Actuator $actuatorKey", "Value is not Boolean")
                                            try {
                                                value = value as Long
                                            }catch (e:Exception){
                                                Log.i("Actuator $actuatorKey", "Value is not Long")
                                                try {
                                                    value = value as Double
                                                }catch (e:Exception){
                                                    try {
                                                        value = value as String
                                                    }catch (e:Exception){
                                                        Log.i("Actuator $actuatorKey", "Value is not String")
                                                        value = 0L
                                                    }
                                                }
                                            }
                                        }

                                        val objActuator = ObjActuator(
                                            id = actuatorKey.toString(),
                                            name = name ?: "",
                                            type = type,
                                            value = value,
                                            idDevice = "$deviceKey",
                                            nameDevice = nameDevice.toString()
                                        )
                                        dataActuators = dataActuators.plus(objActuator)
                                    }
                                }
                            }
                            viewModelActuator.getActuators(dataActuators)
                        }
                        else{
                            viewModelActuator.getActuators(emptyList())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        uiSupport.showErrorAlertDialog(
                            activity,
                            activity.getString(R.string.error),
                            error.toString()
                        )
                    }

                })

            }

        }
    }

    suspend fun newActuator(idActuator : String, nameActuator : String, typeActuator : Int, idDevice : String) : Boolean{

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val referenceDb = Firebase.database(firebaseApp).reference
        val referenceDevice = referenceDb.child(uid).child("devices").child(idDevice)
        val referenceSensor = referenceDevice.child("actuators").child(idActuator)

        val exist = referenceSensor.get().await().value
        if (exist==null){
            referenceSensor.setValue(hashMapOf(
                "name" to nameActuator,
                "type" to typeActuator)
            )
        }

        return exist==null
    }

    suspend fun editActuator(idActuator : String, nameActuator : String, typeActuator : Int): Boolean {


        val idDevice = idActuator.split("-")[0]

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val referenceDb = Firebase.database(firebaseApp).reference
        val referenceDevice = referenceDb.child(uid).child("devices").child(idDevice)
        val referenceActuator = referenceDevice.child("actuators").child(idActuator)

        referenceActuator.updateChildren(mapOf(
            "name" to nameActuator,
            "type" to typeActuator)
        )

        return true

    }

    suspend fun removeActuators(listRemove: List<ObjActuator>, oldList: List<ObjActuator>) : List<ObjActuator>{

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val referenceDb = Firebase.database(firebaseApp).reference
        val referenceActuators = referenceDb.child(uid).child("devices")

        var newList = oldList

        for (actuator in listRemove){

            val referenceActuator = referenceActuators.child(actuator.idDevice).child("actuators").child(actuator.id)
            referenceActuator.removeValue().await()
            newList = newList.minus(actuator)
        }

        return newList
    }

}