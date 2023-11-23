package com.silentbit.thingshubmobile.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silentbit.thingshubmobile.databinding.InitSelecDataserverBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitSelecDataserver : AppCompatActivity() {

    lateinit var binding : InitSelecDataserverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InitSelecDataserverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectDataserverNext.setOnClickListener {
            val intent = Intent(this, InitFirebaseInstructions::class.java)
            startActivity(intent)
        }

        binding.selecRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            binding.selectDataserverNext.isEnabled = true
        }

    }
}