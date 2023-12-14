package com.silentbit.thingshubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.thingshubmobile.domain.EditDeviceCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelDeviceEdit @Inject constructor(
    private val editDeviceCase : EditDeviceCase
): ViewModel(){

    private var isLoading = MutableLiveData<Boolean>()
    var isSaveDone = MutableLiveData<Boolean>()

    fun editDevice(name: String, description: String, identification: String){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO){
            val status = editDeviceCase(name, description, identification)
            withContext(Dispatchers.Main){
                isSaveDone.postValue(status)
                isLoading.postValue(false)
            }
        }

    }

}