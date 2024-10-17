package com.silentbit.sensehubmobile.ui.view.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silentbit.sensehubmobile.databinding.SliderFirebaseInst1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderFirebaseInst1 : Fragment() {

    private var _binding : SliderFirebaseInst1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SliderFirebaseInst1Binding.inflate(layoutInflater, container,false)
        return binding.root
    }

}