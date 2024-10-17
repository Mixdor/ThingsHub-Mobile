package com.silentbit.sensehubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseDevice
import com.silentbit.sensehubmobile.databinding.FragSensorsBinding
import com.silentbit.sensehubmobile.domain.GetSensorsCase
import com.silentbit.sensehubmobile.domain.objs.ObjSensor
import com.silentbit.sensehubmobile.ui.adapters.AdapterSensors
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelSensor
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
    @Inject lateinit var firebaseDevice: FirebaseDevice
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
            binding.fabSensorEdit.isVisible = false
            binding.fabSensorRemove.isVisible = false
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

            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.to_remove, null)

            val textView = dialogView.findViewById<TextView>(R.id.txtToRemove)
            var stringToRemove = ""
            for(sensor in sensorsAdapter.dataChecked){
                stringToRemove += "- ${sensor.name}\n"
            }
            textView.text = StringBuilder(stringToRemove)

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.warning))
                .setMessage(getString(R.string.delete_sensors_data_confirmation))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.delete_sensors)){ dialog, _ ->

                    viewModelSensor.deleteSensor(
                        sensorsAdapter.dataChecked,
                        sensorsAdapter.data
                    )

                    dialog.dismiss()
                }
                .show()
        }

    }

}