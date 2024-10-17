package com.silentbit.sensehubmobile.ui.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.databinding.FragSensorsEditBinding
import com.silentbit.sensehubmobile.domain.objs.ObjSensor
import com.silentbit.sensehubmobile.support.UiSupport
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelSensorEdit
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import javax.inject.Inject


@AndroidEntryPoint
class FragSensorsEdit : Fragment() {

    private val viewModelSensorEdit : ViewModelSensorEdit by viewModels()
    private var _binding : FragSensorsEditBinding? = null
    private val binding get() = _binding!!
    private var magnitude = 0

    @Inject lateinit var uiSupport : UiSupport

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSensorsEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sensor = arguments?.customGetSerializable<ObjSensor>("sensor")

        binding.txtIdSensor.setText(sensor?.id)
        binding.txtNameSensor.setText(sensor?.name)
        binding.txtIdDevice.setText(buildString {
            append(sensor?.idDevice)
            append(" - ")
            append(sensor?.nameDevice)
        })
        binding.checkIsPercentage.isChecked = sensor?.isPercentage ?: false

        val listMagnitudes = uiSupport.loadMagnitudes(binding.txtMagnitude)
        var numMagnitude = sensor?.magnitude?.toInt() ?: 0
        val selectedMagnitude = listMagnitudes[numMagnitude]
        binding.txtMagnitude.setText(selectedMagnitude.name)
        binding.magnitudeLayout.startIconDrawable = ContextCompat.getDrawable(
            requireContext(), selectedMagnitude.idImage
        )

        binding.txtMagnitude.setOnItemClickListener { adapterView, viewAdpt, position, l ->

            numMagnitude = position

            magnitude = listMagnitudes[position].idImage
            binding.magnitudeLayout.startIconDrawable = ContextCompat.getDrawable(
                requireContext(),
                magnitude
            )
        }

        binding.btnEditSensor.setOnClickListener{

            if (binding.txtIdSensor.text?.isNotEmpty() == true &&
                binding.txtNameSensor.text?.isNotEmpty() == true &&
                binding.txtMagnitude.text?.isNotEmpty() == true) {

                val idSensor = binding.txtIdSensor.text.toString()
                val nameSensor = binding.txtNameSensor.text.toString()
                val magnitude = numMagnitude
                val isPercentage = binding.checkIsPercentage.isChecked

                viewModelSensorEdit.editSensor(
                    idSensor, nameSensor, magnitude, isPercentage
                )
            }
            else {
                Toast.makeText(
                    context,
                    getString(R.string.please_complete_ranges),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModelSensorEdit.isEditDone.observe(requireActivity()) {
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