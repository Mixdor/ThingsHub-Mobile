package com.silentbit.thingshubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.databinding.FragDeviceAddBinding
import com.silentbit.thingshubmobile.ui.viewmodel.ViewModelDeviceAdd
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FragDeviceAdd : Fragment() {

    private val viewModelDeviceAdd : ViewModelDeviceAdd by viewModels()
    private var _binding : FragDeviceAddBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var dataStoreManager : DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDeviceAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddNewDevice.setOnClickListener {

            val name = binding.txtNameDevice.text.toString()
            val description = binding.txtDescriptionDevice.text.toString()
            val identification = binding.txtIdDevice.text.toString()

            viewModelDeviceAdd.newDevice(name, description, identification)

        }

        viewModelDeviceAdd.isSaveDone.observe(requireActivity()){

            if (it){
                Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context, "Ya existe", Toast.LENGTH_LONG).show()
            }

            binding.txtIdDevice.setText("")
            binding.txtNameDevice.setText("")
            binding.txtDescriptionDevice.setText("")

        }

    }

}