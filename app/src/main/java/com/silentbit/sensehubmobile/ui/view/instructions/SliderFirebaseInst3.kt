package com.silentbit.sensehubmobile.ui.view.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.databinding.SliderFirebaseInst3Binding
import com.silentbit.sensehubmobile.support.SpanBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SliderFirebaseInst3 : Fragment() {

    private var _binding : SliderFirebaseInst3Binding? = null
    private val binding get() = _binding!!
    @Inject lateinit var spanBuilder : SpanBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = SliderFirebaseInst3Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spanBuilder.setSpanBold(
            binding.goToCompileTabAuthentication,
            listOf(getString(R.string.compilation), getString(R.string.authentication))
        )
    }

}