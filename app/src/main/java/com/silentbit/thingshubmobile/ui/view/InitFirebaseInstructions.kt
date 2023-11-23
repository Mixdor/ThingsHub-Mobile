package com.silentbit.thingshubmobile.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.silentbit.thingshubmobile.databinding.InitFirebaseInstructionsBinding
import com.silentbit.thingshubmobile.ui.adapters.InstructionsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitFirebaseInstructions : AppCompatActivity() {

    private lateinit var binding : InitFirebaseInstructionsBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InitFirebaseInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.pager2
        viewPager.adapter = InstructionsPagerAdapter(this)

        binding.firebaseSkip.setOnClickListener {
            val intent = Intent(this, InitFirebaseCredentials::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }


}