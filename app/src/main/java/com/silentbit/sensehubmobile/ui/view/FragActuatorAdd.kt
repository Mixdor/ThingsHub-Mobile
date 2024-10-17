package com.silentbit.sensehubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.databinding.FragActuatorAddBinding
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelActuatorAdd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragActuatorAdd : Fragment() {

    private val viewModelActuatorAdd : ViewModelActuatorAdd by viewModels()
    private var _binding : FragActuatorAddBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragActuatorAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (binding.txtType as? MaterialAutoCompleteTextView)?.setSimpleItems(arrayOf(getString(R.string.toggle)))
        binding.txtType.setText(getString(R.string.toggle))

        viewModelActuatorAdd.getDevices()

        viewModelActuatorAdd.devices.observe(requireActivity()) { listDevices ->

            val arrayDevice = listDevices.map {
                "${it.id} - ${it.name}"
            }.toTypedArray()
            (binding.txtDevice as? MaterialAutoCompleteTextView)?.setSimpleItems(arrayDevice)
        }

        viewModelActuatorAdd.isSaveDone.observe(requireActivity()){
            if (it){
                Toast.makeText(context, getString(R.string.saved_successfully), Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context, getString(R.string.already_exists), Toast.LENGTH_LONG).show()
            }
        }

        binding.btnNewActuator.setOnClickListener {

            if (binding.txtIdActuator.text?.isNotEmpty() == true &&
                binding.txtNameActuator.text?.isNotEmpty() == true &&
                binding.txtType.text?.isNotEmpty() == true &&
                binding.txtDevice.text?.isNotEmpty() == true){

                val idDevice = binding.txtDevice.text.toString().split(" -")[0]
                val idActuator = "$idDevice-${binding.txtIdActuator.text.toString()}"

                val type = when(binding.txtType.text.toString()){
                    getString(R.string.toggle) -> 1
                    else -> {0}
                }

                viewModelActuatorAdd.newActuator(
                    idActuator = idActuator,
                    nameActuator = binding.txtNameActuator.text.toString(),
                    typeActuator = type,
                    idDevice = idDevice
                )
            }
            else{
                Toast.makeText(context, getString(R.string.please_complete_all_fields), Toast.LENGTH_LONG).show()
            }
        }
    }

}