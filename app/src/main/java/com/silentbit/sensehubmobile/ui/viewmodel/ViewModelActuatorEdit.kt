package com.silentbit.sensehubmobile.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silentbit.sensehubmobile.domain.EditActuatorCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelActuatorEdit @Inject constructor(
    private val editActuatorCase : EditActuatorCase
) : ViewModel(){

    private val isLoading = MutableLiveData<Boolean>()
    var isEditDone = MutableLiveData<Boolean>()

    fun editActuator(idActuator : String, nameActuator : String, typeActuator : Int){

        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO){
            val status = editActuatorCase(idActuator, nameActuator, typeActuator)
            withContext(Dispatchers.Main){
                isEditDone.postValue(status)
                isLoading.postValue(false)
            }
        }

    }

}