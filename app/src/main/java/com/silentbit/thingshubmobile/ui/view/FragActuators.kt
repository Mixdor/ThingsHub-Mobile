package com.silentbit.thingshubmobile.ui.view

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
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.databinding.FragActuatorsBinding
import com.silentbit.thingshubmobile.domain.GetActuatorCase
import com.silentbit.thingshubmobile.domain.objs.ObjActuator
import com.silentbit.thingshubmobile.ui.adapters.AdapterActuators
import com.silentbit.thingshubmobile.ui.viewmodel.ViewModelActuator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FragActuators : Fragment() {

    private val viewModelActuator : ViewModelActuator by viewModels()
    private var _binding : FragActuatorsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var getActuatorCase : GetActuatorCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragActuatorsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actuatorAdapter = AdapterActuators(listOf(), binding)
        binding.recyclerActuators.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = actuatorAdapter
        }

        getActuatorCase(requireActivity(), viewModelActuator)

        viewModelActuator.actuators.observe(requireActivity()){actuators ->
            if (actuators.isNotEmpty()){
                lifecycleScope.launch(Dispatchers.IO){
                    var newActuator : List<ObjActuator> = listOf()
                    for (actuator in actuators){
                        withContext(Dispatchers.Main){
                            newActuator = newActuator.plus(actuator)
                            actuatorAdapter.updateData(newActuator)
                        }
                    }
                }
            }
        }

        viewModelActuator.isLoading.observe(requireActivity()){
            binding.loadingActuators.isVisible = it
            binding.fabActuatorEdit.isVisible = false
            binding.fabActuatorRemove.isVisible = false
        }

        binding.fabActuatorAdd.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_fragActuators_to_fragActuatorAdd)
        }

        binding.fabActuatorEdit.setOnClickListener {

            val bundle = Bundle()
            for (objActuator in actuatorAdapter.dataChecked){
                bundle.putSerializable("actuator", objActuator)
            }
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_fragActuators_to_fragActuatorEdit, bundle)
        }

        binding.fabActuatorRemove.setOnClickListener {
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.to_remove, null)

            val textView = dialogView.findViewById<TextView>(R.id.txtToRemove)
            var stringToRemove = ""
            for(sensor in actuatorAdapter.dataChecked){
                stringToRemove += "- ${sensor.name}\n"
            }
            textView.text = StringBuilder(stringToRemove)

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.warning))
                .setMessage(getString(R.string.delete_actuators_data_confirmation))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.delete_actuators)){ dialog, _ ->

                    viewModelActuator.deleteActuators(
                        actuatorAdapter.dataChecked,
                        actuatorAdapter.data
                    )

                    dialog.dismiss()
                }
                .show()
        }

    }
}