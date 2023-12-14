package com.silentbit.thingshubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.silentbit.thingshubmobile.databinding.FragDeviceEditBinding
import com.silentbit.thingshubmobile.domain.objs.ObjDevice
import com.silentbit.thingshubmobile.ui.viewmodel.ViewModelDeviceEdit
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragDeviceEdit : Fragment() {

    private val viewModelDeviceEdit : ViewModelDeviceEdit by viewModels()
    private var _binding : FragDeviceEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDeviceEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val device = arguments?.getSerializable("device") as ObjDevice

        binding.txtIdDevice.setText(device.id)
        binding.txtNameDevice.setText(device.name)
        binding.txtDescriptionDevice.setText(device.description)


        binding.btnApplyChange.setOnClickListener {

            val name = binding.txtNameDevice.text.toString()
            val description = binding.txtDescriptionDevice.text.toString()
            val identification = binding.txtIdDevice.text.toString()

            viewModelDeviceEdit.editDevice(name, description, identification)
        }

        viewModelDeviceEdit.isSaveDone.observe(requireActivity()){

            if (it){
                Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context, "Ya existe", Toast.LENGTH_LONG).show()
            }

        }

    }

}