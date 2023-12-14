package com.silentbit.thingshubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.thingshubmobile.domain.RemoveDeviceCase
import com.silentbit.thingshubmobile.domain.objs.ObjDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelDevice @Inject constructor(
    private val removeDeviceCase: RemoveDeviceCase
) : ViewModel() {

    var devices = MutableLiveData<List<ObjDevice>>()
    var isLoading = MutableLiveData<Boolean>()

    fun getDevices(data:List<ObjDevice>){
        isLoading.postValue(true)
        devices.postValue(data)
        isLoading.postValue(false)
    }

    fun deleteDevice(dataToDelete: List<ObjDevice>, oldList: List<ObjDevice>) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO){
            val newList = removeDeviceCase(dataToDelete, oldList)
            withContext(Dispatchers.Main){
                devices.postValue(newList)
            }
        }
        isLoading.postValue(false)
    }

}