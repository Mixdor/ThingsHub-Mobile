package com.silentbit.thingshubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silentbit.thingshubmobile.databinding.FragDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragDashboard : Fragment() {

    private var _binding : FragDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}