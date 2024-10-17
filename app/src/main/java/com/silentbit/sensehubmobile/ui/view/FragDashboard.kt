package com.silentbit.sensehubmobile.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.silentbit.sensehubmobile.databinding.FragDashboardBinding
import com.silentbit.sensehubmobile.domain.GetSensorsAndActuators
import com.silentbit.sensehubmobile.ui.adapters.AdapterDashboard
import com.silentbit.sensehubmobile.ui.viewmodel.ViewModelDashboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragDashboard : Fragment() {

    private val viewModelDashboard : ViewModelDashboard by viewModels()
    private var _binding : FragDashboardBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var getSensorsAndActuators : GetSensorsAndActuators

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dashboardAdapter = AdapterDashboard(listOf(), viewModelDashboard)
        binding.recyclerDashboard.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = dashboardAdapter
        }

        getSensorsAndActuators(requireActivity(), viewModelDashboard)

        viewModelDashboard.items.observe(requireActivity()){items ->
            if(items.isNotEmpty()){
                dashboardAdapter.updateData(items)
            }
        }

        viewModelDashboard.isLoading.observe(requireActivity()){
            binding.loadingDashboard.isVisible = it
        }

    }

}