package com.silentbit.thingshubmobile.data.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.domain.objs.ObjSensor
import com.silentbit.thingshubmobile.support.UiSupport
import com.silentbit.thingshubmobile.ui.viewmodel.ViewModelSensor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseSensor @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val uiSupport: UiSupport
) {

    fun getSensors(activity: Activity, viewModelSensor: ViewModelSensor) {

        CoroutineScope(Dispatchers.IO).launch {

            val firebaseApp = dataStoreManager.loadFirebaseApp()
            val uid = Firebase.auth(firebaseApp).uid.toString()
            val reference = Firebase.database(firebaseApp).reference.child(uid).child("devices")

            withContext(Dispatchers.Main) {

                reference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value!=null){

                            var dataSensors : List<ObjSensor> = listOf()

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
                                        val value = sensor["value"] as Long?
                                        val nameDevice = device["name"] as String?

                                        val objSensor = ObjSensor(
                                            id = sensorKey.toString(),
                                            name = name ?: "",
                                            value = value ?: 0,
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
                            viewModelSensor.getSensors(dataSensors)
                        }
                        else{
                            viewModelSensor.getSensors(emptyList())
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

    suspend fun newSensor(idSensor:String, nameSensor:String, typeSensor:Int, idDevice:String, isPercentage:Boolean, enableRanges:Boolean, ranges:String):Boolean{

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val referenceDb = Firebase.database(firebaseApp).reference
        val referenceDevice = referenceDb.child(uid).child("devices").child(idDevice)
        val referenceSensor = referenceDevice.child("sensors").child(idSensor)

        val exist = referenceSensor.get().await().value
        if (exist==null){
            referenceSensor.setValue(hashMapOf(
                "name" to nameSensor,
                "magnitude" to typeSensor,
                "isPercentage" to isPercentage,
                "isEnableRanges" to enableRanges,
                "ranges" to ranges)
            )
        }

        return exist==null
    }

    suspend fun editDevice(idSensor: String, nameSensor: String, magnitude: Int, percentage: Boolean, enableRanges: Boolean, ranges: String): Boolean {


        val idDevice = idSensor.split("-")[0]

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val referenceDb = Firebase.database(firebaseApp).reference
        val referenceDevice = referenceDb.child(uid).child("devices").child(idDevice)
        val referenceSensor = referenceDevice.child("sensors").child(idSensor)

        referenceSensor.setValue(hashMapOf(
            "name" to nameSensor,
            "magnitude" to magnitude,
            "isPercentage" to percentage,
            "isEnableRanges" to enableRanges,
            "ranges" to ranges
        ))

        return true

    }

    suspend fun removeSensor(listRemove: List<ObjSensor>, oldList: List<ObjSensor>): List<ObjSensor>{

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val referenceDb = Firebase.database(firebaseApp).reference
        val referenceDevices = referenceDb.child(uid).child("devices")

        var newList = oldList

        Log.e("listRemove", listRemove.toString())
        for (sensor in listRemove){
            Log.e("sensor", sensor.toString())
            val referenceSensor = referenceDevices.child(sensor.idDevice).child("sensors").child(sensor.id)
            referenceSensor.removeValue().await()
            newList = newList.minus(sensor)
        }

        return newList

    }

}