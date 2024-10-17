package com.silentbit.sensehubmobile.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.silentbit.sensehubmobile.R
import com.silentbit.sensehubmobile.databinding.InitFirebaseInstructionsBinding
import com.silentbit.sensehubmobile.ui.adapters.InstructionsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InitFirebaseInstructions : AppCompatActivity() {

    private lateinit var binding : InitFirebaseInstructionsBinding
    private lateinit var layoutIndicator: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = InitFirebaseInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.pager2
        viewPager.adapter = InstructionsPagerAdapter(this)

        layoutIndicator = binding.layoutIndicator
        val viewPagerSize = (viewPager.adapter)?.itemCount?.minus(1) ?: 0

        for (i in 0 .. viewPagerSize) {
            val circle = ImageView(this)
            circle.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.twotone_circle_24))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            circle.layoutParams = params
            layoutIndicator.addView(circle)
        }

        screenSplash.setKeepOnScreenCondition{false}

        binding.btnLeft.setOnClickListener {
            val intent = Intent(this, InitFirebaseCredentials::class.java)
            startActivity(intent)
        }

        binding.btnRight.setOnClickListener {
            if (viewPager.currentItem < viewPagerSize){
                viewPager.currentItem += 1
            }
            else{
                val intent = Intent(this, InitFirebaseCredentials::class.java)
                startActivity(intent)
            }
        }

        viewPager.registerOnPageChangeCallback( object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                updateIndicator(position)
                if (position>=viewPagerSize){
                    binding.btnLeft.visibility = View.GONE
                    binding.btnRight.text = getString(R.string.skip)
                }
                else{
                    binding.btnLeft.visibility = View.VISIBLE
                    binding.btnRight.text = getString(R.string.next)
                }
                super.onPageSelected(position)
            }
        })

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */) {
            // Back is pressed... Finishing the activity
            if (viewPager.currentItem == 0) {
                finish()
            } else {
                viewPager.currentItem -= 1
            }
        }
    }

    private fun updateIndicator(currentPos: Int) {
        for (i in 0 until layoutIndicator.childCount) {
            val circle = layoutIndicator.getChildAt(i) as ImageView
            if (i == currentPos) {
                circle.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_circle_24))
            } else {
                circle.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.twotone_circle_24))
            }
        }
    }


}