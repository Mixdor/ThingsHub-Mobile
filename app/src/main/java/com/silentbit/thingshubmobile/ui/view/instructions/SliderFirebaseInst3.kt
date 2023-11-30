package com.silentbit.thingshubmobile.ui.view.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silentbit.thingshubmobile.databinding.SliderFirebaseInst3Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderFirebaseInst3 : Fragment() {

    private var _binding : SliderFirebaseInst3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = SliderFirebaseInst3Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

}