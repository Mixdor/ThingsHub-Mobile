package com.silentbit.thingshubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silentbit.thingshubmobile.databinding.FragDeviceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragDevice : Fragment() {

    private var _binding : FragDeviceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDeviceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}