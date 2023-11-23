package com.silentbit.thingshubmobile.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.silentbit.thingshubmobile.ui.view.SliderFirebaseInstructions

private const val NUM_PAGES = 2

class InstructionsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return SliderFirebaseInstructions()
    }

}
