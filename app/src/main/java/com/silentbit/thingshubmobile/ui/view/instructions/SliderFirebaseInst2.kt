package com.silentbit.thingshubmobile.ui.view.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.databinding.SliderFirebaseInst2Binding
import com.silentbit.thingshubmobile.support.SpanBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SliderFirebaseInst2 : Fragment() {

    private var _binding : SliderFirebaseInst2Binding? = null
    private val binding get() = _binding!!
    @Inject lateinit var spanBuilder : SpanBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SliderFirebaseInst2Binding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spanBuilder.setSpanBold(
            binding.txtGoToCompilationTab,
            listOf(getString(R.string.realtime_database), getString(R.string.compilation))
        )

        spanBuilder.setSpanBold(
            binding.goToRealtimeDatabaseRules,
            getString(R.string.rules)
        )

    }

}