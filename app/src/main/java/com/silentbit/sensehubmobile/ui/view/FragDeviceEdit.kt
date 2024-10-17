package com.silentbit.sensehubmobile.ui.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.databinding.FragDeviceEditBinding
import com.silentbit.sensehubmobile.domain.objs.ObjDevice
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelDeviceEdit
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable


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

        val device = arguments?.customGetSerializable<ObjDevice>("device")

        binding.txtIdDevice.setText(device?.id)
        binding.txtNameDevice.setText(device?.name)
        binding.txtDescriptionDevice.setText(device?.description)


        binding.btnApplyChange.setOnClickListener {

            val name = binding.txtNameDevice.text.toString()
            val description = binding.txtDescriptionDevice.text.toString()
            val identification = binding.txtIdDevice.text.toString()

            viewModelDeviceEdit.editDevice(name, description, identification)
        }

        viewModelDeviceEdit.isSaveDone.observe(requireActivity()){
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