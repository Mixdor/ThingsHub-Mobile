package com.silentbit.thingshubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.thingshubmobile.domain.GetDevicesCase
import com.silentbit.thingshubmobile.domain.objs.ObjDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelSensorAdd @Inject constructor(
    private val getDevicesCase : GetDevicesCase
) : ViewModel() {

    val devices = MutableLiveData<List<ObjDevice>>()

    fun getDevices(){

        viewModelScope.launch(Dispatchers.IO){
            val listDevices = getDevicesCase()
            withContext(Dispatchers.Main){
                devices.postValue(listDevices)
            }
        }



    }


}