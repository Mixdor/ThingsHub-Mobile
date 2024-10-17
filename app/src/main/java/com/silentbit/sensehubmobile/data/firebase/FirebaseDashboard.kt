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
import com.silentbit.sensehubmobile.domain.objs.ObjSensor
import com.silentbit.sensehubmobile.support.UiSupport
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelDashboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseDashboard @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val uiSupport: UiSupport
) {

    fun getSensorsAndActuator(activity: Activity, viewModelDashboard: ViewModelDashboard) {

        CoroutineScope(Dispatchers.IO).launch {

            val firebaseApp = dataStoreManager.loadFirebaseApp()
            val uid = Firebase.auth(firebaseApp).uid.toString()
            val reference = Firebase.database(firebaseApp).reference.child(uid).child("devices")

            withContext(Dispatchers.Main) {

                reference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value!=null){

                            var dataItems : List<Any> = listOf()
                            var dataSensors : List<ObjSensor> = listOf()
                            var dataActuators : List<ObjActuator> = listOf()

                            val devices = snapshot.value as Map<*,*>
                            for(deviceKey in devices.keys) {
                                val device = devices[deviceKey] as Map<*, *>
                                val sensors = device["sensors"] as Map<*, *>?

                                if(sensors!=null){
                                    for(sensorKey in sensors.keys){
                                        val sensor = sensors[sensorKey] as Map<*,*>
                                        val name = sensor["name"] as String?
                                        val magnitude = sensor["magnitude"] as Long?
                                        val isPercentage = sensor["isPercentage"] as Boolean?
                                        val isEnableRanges = sensor["isEnableRanges"] as Boolean?
                                        val ranges = sensor["ranges"] as String?
                                        val nameDevice = device["name"] as String?

                                        var value = sensor["value"]

                                        try {
                                            value = value as Boolean
                                        }catch (e:Exception){
                                            Log.i("Sensor $sensorKey", "Value is not Boolean")
                                            try {
                                                value = value as Long
                                            }catch (e:Exception){
                                                Log.i("Sensor $sensorKey", "Value is not Long")
                                                try {
                                                    value = value as Double
                                                }catch (e:Exception){
                                                    try {
                                                        value = value as String
                                                    }catch (e:Exception){
                                                        Log.i("Sensor $sensorKey", "Value is not String")
                                                        value = 0L
                                                    }
                                                }
                                            }
                                        }

                                        val objSensor = ObjSensor(
                                            id = sensorKey.toString(),
                                            name = name ?: "",
                                            value = value,
                                            magnitude = magnitude ?: 0L,
                                            isPercentage = isPercentage ?: false,
                                            isEnableRanges = isEnableRanges ?: false,
                                            rangeRegular = ranges?.split("::")?.get(0) ?: "",
                                            rangeWarning = ranges?.split("::")?.get(1) ?: "",
                                            idDevice = "$deviceKey",
                                            nameDevice = nameDevice.toString()
                                        )
                                        dataSensors = dataSensors.plus(objSensor)
                                    }
                                }
                            }

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

                            dataItems = dataItems.plus(dataSensors)
                            dataItems = dataItems.plus(dataActuators)

                            dataItems = dataItems.sortedBy {
                                return@sortedBy when(it){
                                    is ObjSensor -> it.name
                                    is ObjActuator -> it.name
                                    else -> {""}
                                }
                            }
                            viewModelDashboard.getItems(dataItems)
                        }
                        else{
                            viewModelDashboard.getItems(emptyList())
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

    suspend fun setValueActuator(idActuator:String, value:Any){

        val idDevice = idActuator.split("-")[0]

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val referenceDb = Firebase.database(firebaseApp).reference
        val referenceDevice = referenceDb.child(uid).child("devices").child(idDevice)
        val referenceActuator = referenceDevice.child("actuators").child(idActuator)

        referenceActuator.updateChildren(mapOf("value" to value))

    }

}