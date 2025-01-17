package com.silentbit.sensehubmobile.data.firebase

import android.app.Activity
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.domain.objs.ObjDevice
import com.silentbit.sensehubmobile.support.UiSupport
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseDevice @Inject constructor(
    private val dataStoreManager : DataStoreManager,
    private val uiSupport: UiSupport,
    @ApplicationContext val context: Context
) {

    suspend fun getDevices(): List<ObjDevice> {

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val reference = Firebase.database(firebaseApp).reference.child(uid).child("devices")

        val result = reference.get().await()

        if (result.value!=null){
            var dataDevice : List<ObjDevice> = listOf()
            val devices = result.value as Map<*,*>

            for(deviceKey in devices.keys){
                val device = devices[deviceKey] as Map<*,*>
                val name = device["name"] as String?
                val description = device["description"] as String?
                val sensors = device["sensors"] as Map<*, *>?
                val actuators = device["actuators"] as Map<*, *>?
                val objDevice = ObjDevice(
                    id = deviceKey.toString(),
                    name = name ?: "",
                    description = description ?: "",
                    sensors = sensors?.size ?: 0,
                    actuators = actuators?.size ?: 0
                )
                dataDevice = dataDevice.plus(objDevice)
            }
            return dataDevice
        }
        else{
            return emptyList()
        }


    }

    fun getDevicesListener(activity:Activity, viewModelDevice:ViewModelDevice){

        CoroutineScope(Dispatchers.IO).launch{
            val firebaseApp = dataStoreManager.loadFirebaseApp()
            withContext(Dispatchers.Main){

                val uid = Firebase.auth(firebaseApp).uid.toString()
                val reference = Firebase.database(firebaseApp).reference.child(uid).child("devices")

                reference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value!=null){
                            var dataDevice : List<ObjDevice> = listOf()
                            val devices = snapshot.value as Map<*,*>

                            for(deviceKey in devices.keys){
                                val device = devices[deviceKey] as Map<*,*>
                                val name = device["name"] as String?
                                val description = device["description"] as String?
                                val sensors = device["sensors"] as Map<*, *>?
                                val actuators = device["actuators"] as Map<*, *>?
                                val objDevice = ObjDevice(
                                    id = deviceKey.toString(),
                                    name = name ?: "",
                                    description = description ?: "",
                                    sensors = sensors?.size ?: 0,
                                    actuators = actuators?.size ?: 0
                                )
                                dataDevice = dataDevice.plus(objDevice)
                            }
                            viewModelDevice.getDevices(dataDevice)
                        }
                        else{
                            viewModelDevice.getDevices(emptyList())
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

    suspend fun newDevice(name:String, description:String, identification:String): Boolean {

        if (identification.isNotEmpty() && name.isNotEmpty()){

            val firebaseApp = dataStoreManager.loadFirebaseApp()
            val uid = Firebase.auth(firebaseApp).uid.toString()
            val referenceDb = Firebase.database(firebaseApp).reference
            val referenceDevices = referenceDb.child(uid).child("devices")
            val referenceDevice = referenceDevices.child(identification)

            val exist = referenceDevice.get().await().value
            if (exist==null){
                referenceDevice.child("name").setValue(name)
                referenceDevice.child("description").setValue(description)
            }

            return exist==null

        }
        else{
            return false
        }

    }

    suspend fun editDevice(name: String, description: String, identification: String): Boolean {

        if (name.isNotEmpty()){

            val firebaseApp = dataStoreManager.loadFirebaseApp()
            val uid = Firebase.auth(firebaseApp).uid.toString()
            val referenceDb = Firebase.database(firebaseApp).reference
            val referenceDevices = referenceDb.child(uid).child("devices")
            val referenceDevice = referenceDevices.child(identification)

            referenceDevice.child("name").setValue(name)
            referenceDevice.child("description").setValue(description)

            return true

        }
        else{
            return false
        }

    }

    suspend fun removeDevice(listRemove: List<ObjDevice>, oldList: List<ObjDevice>): List<ObjDevice> {

        val firebaseApp = dataStoreManager.loadFirebaseApp()
        val uid = Firebase.auth(firebaseApp).uid.toString()
        val referenceDb = Firebase.database(firebaseApp).reference
        val referenceDevices = referenceDb.child(uid).child("devices")

        var newList = oldList
        for (device in listRemove){
            val referenceDevice = referenceDevices.child(device.id)
            referenceDevice.removeValue()
            newList = newList.minus(device)
        }
        return newList

    }

}