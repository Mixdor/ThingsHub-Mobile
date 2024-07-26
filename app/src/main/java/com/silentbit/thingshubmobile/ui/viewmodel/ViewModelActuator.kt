package com.silentbit.thingshubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.thingshubmobile.domain.RemoveActuatorCase
import com.silentbit.thingshubmobile.domain.objs.ObjActuator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelActuator @Inject constructor(
    private val removeActuatorCase : RemoveActuatorCase
) : ViewModel() {

    var actuators = MutableLiveData<List<ObjActuator>>()
    var isLoading = MutableLiveData<Boolean>()

    fun getActuators(inData:List<ObjActuator>){
        isLoading.postValue(true)
        actuators.postValue(inData)
        isLoading.postValue(false)
    }

    fun deleteActuators(dataToDelete: List<ObjActuator>, oldList: List<ObjActuator>){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO){
            val newList = removeActuatorCase(dataToDelete, oldList)
            withContext(Dispatchers.Main){
                actuators.postValue(newList)
            }
        }
        isLoading.postValue(false)
    }

}