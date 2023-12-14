package com.silentbit.thingshubmobile.domain

import android.content.Context
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseBackend
import com.silentbit.thingshubmobile.domain.objs.ObjDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoveDeviceCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager: DataStoreManager,
    private val firebaseBackend: FirebaseBackend
) {

    suspend operator fun invoke(listRemove:List<ObjDevice>, oldList: List<ObjDevice>) : List<ObjDevice>{
        return when(dataStoreManager.loadTypeServer()){
            context.getString(R.string.firebase) -> firebaseBackend.removeDevice(listRemove, oldList)
            else -> emptyList()
        }
    }
}