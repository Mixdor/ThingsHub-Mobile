package com.silentbit.sensehubmobile.ui.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.databinding.FragActutorEditBinding
import com.silentbit.sensehubmobile.domain.objs.ObjActuator
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelActuatorEdit
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class FragActuatorEdit : Fragment() {

    private val viewModelActuatorEdit : ViewModelActuatorEdit by viewModels()
    private var _binding : FragActutorEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragActutorEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actuator = arguments?.customGetSerializable<ObjActuator>("actuator")

        binding.txtIdActuator.setText(actuator?.id)
        binding.txtNameActuator.setText(actuator?.name)

        binding.txtType.setText(
            when(actuator?.type){
                1 -> getString(R.string.toggle)
                else -> {""}
            }
        )

        binding.txtDevice.setText(buildString {
            append(actuator?.idDevice)
            append(" - ")
            append(actuator?.nameDevice)
        })

        (binding.txtType as? MaterialAutoCompleteTextView)?.setSimpleItems(arrayOf(getString(R.string.toggle)))

        binding.btnEditActuator.setOnClickListener {

            if(binding.txtIdActuator.text?.isNotEmpty() == true &&
                binding.txtNameActuator.text?.isNotEmpty() == true
            ){

                val id = binding.txtIdActuator.text.toString()
                val name = binding.txtNameActuator.text.toString()

                val type = when(binding.txtType.text.toString()){
                    "Toggle" -> 1
                    else -> {0}
                }

                viewModelActuatorEdit.editActuator(
                    idActuator = id,
                    nameActuator = name,
                    typeActuator = type
                )

            }
        }

        viewModelActuatorEdit.isEditDone.observe(requireActivity()) {
            if (it){
                Toast.makeText(context, getString(R.string.saved_successfully), Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
            }
        }

    }

    @Suppress("DEPRECATION")
    private inline fun <reified T : Serializable> Bundle.customGetSerializable(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getSerializable(key, T::class.java)
        } else {
            getSerializable(key) as? T
        }
    }

}