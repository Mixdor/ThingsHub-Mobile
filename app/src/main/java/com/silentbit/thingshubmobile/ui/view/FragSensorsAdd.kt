package com.silentbit.thingshubmobile.ui.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.databinding.FragSensorsAddBinding
import com.silentbit.thingshubmobile.support.CustomDialogs
import com.silentbit.thingshubmobile.support.UiSupport
import com.silentbit.thingshubmobile.ui.viewmodel.ViewModelSensorAdd
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FragSensorsAdd : Fragment() {

    private val viewModelSensorAdd : ViewModelSensorAdd by viewModels()
    private var _binding : FragSensorsAddBinding? = null
    private val binding get() = _binding!!
    private var magnitude = 0

    @Inject lateinit var customDialogs : CustomDialogs
    @Inject lateinit var uiSupport : UiSupport

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSensorsAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customDialogs.init(requireContext())

        val itemsMagnitude = uiSupport.loadMagnitudes(binding.txtMagnitude)

        binding.chipRangeRegular.typeface = Typeface.create(ResourcesCompat.getFont(requireContext(),R.font.old_standard_tt),Typeface.BOLD)
        binding.chipRangeWarning.typeface = Typeface.create(ResourcesCompat.getFont(requireContext(),R.font.old_standard_tt),Typeface.BOLD)

        var numMagnitude = 0

        binding.txtMagnitude.setOnItemClickListener { adapterView, viewAdpt, position, l ->

            numMagnitude = position

            magnitude = itemsMagnitude[position].idImage
            binding.magnitudeLayout.startIconDrawable = ContextCompat.getDrawable(
                requireContext(),
                magnitude
            )
        }

        viewModelSensorAdd.getDevices()

        viewModelSensorAdd.devices.observe(requireActivity()) { listDevices ->

            val arrayDevice = listDevices.map {
                "${it.id} - ${it.name}"
            }.toTypedArray()
            (binding.txtDevice as? MaterialAutoCompleteTextView)?.setSimpleItems(arrayDevice)
        }

        viewModelSensorAdd.isSaveDone.observe(requireActivity()){
            if (it){
                Toast.makeText(context, getString(R.string.saved_successfully), Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context, getString(R.string.already_exists), Toast.LENGTH_LONG).show()
            }
        }

        binding.checkEnableRange.setOnCheckedChangeListener { _, status ->
            binding.layoutRanges.isVisible = status
        }

        binding.chipRangeRegular.setOnClickListener {
            customDialogs.showDialogRange(binding.chipRangeRegular)
        }

        binding.chipRangeWarning.setOnClickListener {
            customDialogs.showDialogRange(binding.chipRangeWarning)
        }

        binding.btnNewSensor.setOnClickListener {

            if (binding.txtIdSensor.text?.isNotEmpty() == true &&
                binding.txtNameSensor.text?.isNotEmpty() == true &&
                binding.txtMagnitude.text?.isNotEmpty() == true &&
                binding.txtDevice.text?.isNotEmpty() == true){

                val checkRange = binding.checkEnableRange.isChecked
                val checkRegular = binding.chipRangeRegular.text != getString(R.string.modify)
                val checkWarning = binding.chipRangeWarning.text != getString(R.string.modify)

                if ((!checkRange) || (checkRegular && checkWarning)){

                    val idDevice = binding.txtDevice.text.toString().split(" -")[0]
                    val idSensor = "$idDevice-${binding.txtIdSensor.text.toString()}"

                    viewModelSensorAdd.newSensor(
                        idSensor = idSensor,
                        nameSensor = binding.txtNameSensor.text.toString(),
                        typeSensor = numMagnitude,
                        idDevice = idDevice,
                        isPercentage = binding.checkIsPercentage.isChecked,
                        enableRanges = binding.checkEnableRange.isChecked,
                        ranges = "${binding.chipRangeRegular.text}::${binding.chipRangeWarning.text}"
                    )
                }
                else{
                    Toast.makeText(context, getString(R.string.please_complete_ranges), Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(context, getString(R.string.please_complete_all_fields), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("idMagnitude", magnitude)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val idResourceMagnitude = savedInstanceState?.getInt("idMagnitude") ?: 0
        if (idResourceMagnitude!=0){
            binding.magnitudeLayout.startIconDrawable = ContextCompat.getDrawable(
                requireContext(),
                idResourceMagnitude
            )
            magnitude = idResourceMagnitude
        }
    }

}