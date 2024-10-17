package com.silentbit.sensehubmobile.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.silentbit.sensehubmobile.ui.view.instructions.SliderFirebaseInst1
import com.silentbit.sensehubmobile.ui.view.instructions.SliderFirebaseInst2
import com.silentbit.sensehubmobile.ui.view.instructions.SliderFirebaseInst3

private const val NUM_PAGES = 3

class InstructionsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> SliderFirebaseInst1()
            1 -> SliderFirebaseInst2()
            else -> SliderFirebaseInst3()
        }

    }

}
