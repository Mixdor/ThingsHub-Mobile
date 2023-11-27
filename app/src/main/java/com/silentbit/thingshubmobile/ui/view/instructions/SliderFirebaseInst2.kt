package com.silentbit.thingshubmobile.ui.view.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.silentbit.thingshubmobile.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderFirebaseInst2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.slider_firebase_inst2, container, false)
    }


}