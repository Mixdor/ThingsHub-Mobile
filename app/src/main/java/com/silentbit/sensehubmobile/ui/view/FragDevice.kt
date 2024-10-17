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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.data.DataStoreManager
import com.silentbit.sensehubmobile.data.firebase.FirebaseDevice
import com.silentbit.sensehubmobile.databinding.FragDeviceBinding
import com.silentbit.sensehubmobile.domain.GetDevicesListenerCase
import com.silentbit.sensehubmobile.domain.objs.ObjDevice
import com.silentbit.sensehubmobile.ui.adapters.AdapterDevice
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelDevice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FragDevice : Fragment() {

    private val viewModelDevice : ViewModelDevice by viewModels()
    private var _binding : FragDeviceBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var dataStoreManager : DataStoreManager
    @Inject lateinit var firebaseDevice: FirebaseDevice
    @Inject lateinit var getDevicesCase: GetDevicesListenerCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDeviceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val devicesAdapter = AdapterDevice(listOf(),binding)
        binding.recyclerDevices.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = devicesAdapter
        }

        getDevicesCase(requireActivity(), viewModelDevice)

        viewModelDevice.devices.observe(requireActivity()){ devices ->

            if(devices.isNotEmpty()){
                lifecycleScope.launch(Dispatchers.IO){
                    var newDevices : List<ObjDevice> = listOf()
                    for (device in devices){
                        withContext(Dispatchers.Main){
                            newDevices = newDevices.plus(device)
                            devicesAdapter.updateData(newDevices)
                        }
                    }
                }
            }
        }

        viewModelDevice.isLoading.observe(requireActivity()){
            binding.loadingDevices.isVisible = it
            binding.fabDeviceEdit.isVisible = false
            binding.fabDeviceRemove.isVisible = false
        }

        binding.fabDeviceAdd.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_fragDevice_to_fragDeviceAdd)
        }

        binding.fabDeviceEdit.setOnClickListener {

            val bundle = Bundle()
            for (objDevice in devicesAdapter.dataChecked){
                bundle.putSerializable("device", objDevice)
            }

            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_fragDevice_to_fragDeviceEdit, bundle)
        }

        binding.fabDeviceRemove.setOnClickListener {

            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.to_remove, null)

            val textView = dialogView.findViewById<TextView>(R.id.txtToRemove)
            var stringToRemove = ""
            for(device in devicesAdapter.dataChecked){
                stringToRemove += "- ${device.name}\n"
            }
            textView.text = StringBuilder(stringToRemove)

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.warning))
                .setMessage(getString(R.string.delete_data_warning))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.delete_devices)){ dialog, _ ->

                    viewModelDevice.deleteDevice(
                        devicesAdapter.dataChecked,
                        devicesAdapter.data
                    )

                    dialog.dismiss()
                }
                .show()
        }


    }

}