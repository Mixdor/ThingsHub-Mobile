package com.silentbit.thingshubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.thingshubmobile.domain.EditSensorCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelSensorEdit @Inject constructor(
    private val editSensorCase : EditSensorCase
) : ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()
    var isEditDone = MutableLiveData<Boolean>()

    fun editSensor(idSensor: String, nameSensor: String, magnitude: Int, isPercentage: Boolean,
                   isEnableRanges: Boolean, ranges: String) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO){
            val status = editSensorCase(
                idSensor, nameSensor, magnitude, isPercentage, isEnableRanges, ranges
            )
            withContext(Dispatchers.Main){
                isEditDone.postValue(status)
                isLoading.postValue(false)
            }
        }
    }

}