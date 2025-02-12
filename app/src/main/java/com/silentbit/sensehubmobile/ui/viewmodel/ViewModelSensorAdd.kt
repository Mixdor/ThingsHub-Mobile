package com.silentbit.sensehubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.sensehubmobile.domain.GetDevicesCase
import com.silentbit.sensehubmobile.domain.NewSensorCase
import com.silentbit.sensehubmobile.domain.objs.ObjDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelSensorAdd @Inject constructor(
    private val getDevicesCase : GetDevicesCase,
    private val newSensorCase : NewSensorCase
) : ViewModel() {

    val devices = MutableLiveData<List<ObjDevice>>()
    var isSaveDone = MutableLiveData<Boolean>()

    fun getDevices(){

        viewModelScope.launch(Dispatchers.IO){
            val listDevices = getDevicesCase()
            withContext(Dispatchers.Main){
                devices.postValue(listDevices)
            }
        }
    }

    fun newSensor(idSensor:String, nameSensor:String, typeSensor:Int, idDevice:String, isPercentage:Boolean){

        viewModelScope.launch(Dispatchers.IO){
            val result = newSensorCase(idSensor, nameSensor, typeSensor, idDevice, isPercentage)
            withContext(Dispatchers.Main){
                isSaveDone.postValue(result)
            }
        }

    }


}