package com.silentbit.sensehubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.sensehubmobile.domain.SetValueActuatorCase
import com.silentbit.sensehubmobile.domain.objs.ObjActuator
import com.silentbit.sensehubmobile.domain.objs.ObjSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDashboard @Inject constructor(
    private val setValueActuatorCase: SetValueActuatorCase
) : ViewModel() {

    var sensors = MutableLiveData<List<ObjSensor>>()
    var actuators = MutableLiveData<List<ObjActuator>>()

    var items = MutableLiveData<List<Any>>()
    var isLoading = MutableLiveData<Boolean>()


    fun getItems(listItems : List<Any>){
        isLoading.postValue(true)
        items.postValue(listItems)
        isLoading.postValue(false)
    }

    fun setValueActuator(idActuator:String, value:Any){
        viewModelScope.launch(Dispatchers.IO){
            setValueActuatorCase(idActuator, value)
        }
    }

}