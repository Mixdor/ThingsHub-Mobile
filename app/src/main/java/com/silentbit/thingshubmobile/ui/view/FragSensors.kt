package com.silentbit.thingshubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silentbit.thingshubmobile.databinding.FragSensorsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragSensors : Fragment() {

    private var _binding : FragSensorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSensorsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}