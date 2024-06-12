package com.silentbit.thingshubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.thingshubmobile.domain.RemoveSensorCase
import com.silentbit.thingshubmobile.domain.objs.ObjSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelSensor @Inject constructor(
    private val removeSensorCase: RemoveSensorCase
) : ViewModel(){

    var sensors = MutableLiveData<List<ObjSensor>>()
    var isLoading = MutableLiveData<Boolean>()

    fun getSensors(inData:List<ObjSensor>){
        isLoading.postValue(true)
        sensors.postValue(inData)
        isLoading.postValue(false)
    }

    fun deleteDevice(dataToDelete: List<ObjSensor>, oldList: List<ObjSensor>) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO){
            val newList = removeSensorCase(dataToDelete, oldList)
            withContext(Dispatchers.Main){
                sensors.postValue(newList)
            }
        }
        isLoading.postValue(false)
    }

}