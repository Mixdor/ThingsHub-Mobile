package com.silentbit.thingshubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.data.firebase.FirebaseBackend
import com.silentbit.thingshubmobile.databinding.FragSensorsBinding
import com.silentbit.thingshubmobile.domain.GetSensorsCase
import com.silentbit.thingshubmobile.domain.objs.ObjSensor
import com.silentbit.thingshubmobile.ui.adapters.AdapterSensors
import com.silentbit.thingshubmobile.ui.viewmodel.ViewModelSensor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FragSensors : Fragment() {

    private val viewModelSensor : ViewModelSensor by viewModels()
    private var _binding : FragSensorsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var dataStoreManager : DataStoreManager
    @Inject lateinit var firebaseBackend: FirebaseBackend
    @Inject lateinit var getSensorsCase : GetSensorsCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSensorsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sensorsAdapter = AdapterSensors(listOf(), binding)
        binding.recyclerSensors.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = sensorsAdapter
        }

        getSensorsCase(requireActivity(), viewModelSensor)

        viewModelSensor.sensors.observe(requireActivity()){sensors ->
            if(sensors.isNotEmpty()){
                lifecycleScope.launch(Dispatchers.IO){
                    var newSensor : List<ObjSensor> = listOf()
                    for (sensor in sensors){
                        withContext(Dispatchers.Main){
                            newSensor = newSensor.plus(sensor)
                            sensorsAdapter.updateData(newSensor)
                        }
                    }
                }
            }
        }

        viewModelSensor.isLoading.observe(requireActivity()){
            binding.loadingSensors.isVisible = it
        }



        binding.fabSensorAdd.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_fragSensors_to_fragSensorsAdd)
        }

        binding.fabSensorEdit.setOnClickListener {

            val bundle = Bundle()
            for (objSensor in sensorsAdapter.dataChecked){
                bundle.putSerializable("sensor", objSensor)
            }

            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_fragSensors_to_fragSensorsEdit, bundle)
        }

        binding.fabSensorRemove.setOnClickListener {

            

        }

    }

}