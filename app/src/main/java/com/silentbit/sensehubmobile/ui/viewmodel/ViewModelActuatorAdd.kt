package com.silentbit.sensehubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.sensehubmobile.domain.GetDevicesCase
import com.silentbit.sensehubmobile.domain.NewActuatorCase
import com.silentbit.sensehubmobile.domain.objs.ObjDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelActuatorAdd @Inject constructor(
    private val getDevicesCase : GetDevicesCase,
    private val newActuatorCase : NewActuatorCase
) : ViewModel() {

    var devices = MutableLiveData<List<ObjDevice>>()
    var isSaveDone = MutableLiveData<Boolean>()

    fun getDevices(){
        viewModelScope.launch(Dispatchers.IO){
            val listDevices = getDevicesCase()
            withContext(Dispatchers.Main){
                devices.postValue(listDevices)
            }
        }
    }

    fun newActuator(idActuator : String, nameActuator : String, typeActuator : Int, idDevice : String){

        viewModelScope.launch(Dispatchers.IO){
            val result = newActuatorCase(idActuator, nameActuator, typeActuator, idDevice)
            withContext(Dispatchers.Main){
                isSaveDone.postValue(result)
            }
        }

    }

}