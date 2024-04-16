package com.silentbit.thingshubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silentbit.thingshubmobile.domain.objs.ObjSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelSensor @Inject constructor(

) : ViewModel(){

    var sensors = MutableLiveData<List<ObjSensor>>()
    var isLoading = MutableLiveData<Boolean>()

    fun getSensors(inData:List<ObjSensor>){
        isLoading.postValue(true)
        sensors.postValue(inData)
        isLoading.postValue(false)
    }

}