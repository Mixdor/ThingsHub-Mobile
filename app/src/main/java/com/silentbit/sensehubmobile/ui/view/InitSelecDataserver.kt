package com.silentbit.sensehubmobile.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.silentbit.sensehubmobile.databinding.InitSelecDataserverBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitSelecDataserver : AppCompatActivity() {

    lateinit var binding : InitSelecDataserverBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = InitSelecDataserverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenSplash.setKeepOnScreenCondition{false}

        binding.selectDataserverNext.setOnClickListener {
            val intent = Intent(this, InitFirebaseInstructions::class.java)
            startActivity(intent)
        }

        binding.selecRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            binding.selectDataserverNext.isEnabled = true
        }

    }
}