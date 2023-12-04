package com.silentbit.thingshubmobile.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.data.DataStoreManager
import com.silentbit.thingshubmobile.databinding.ActivityMainBinding
import com.silentbit.thingshubmobile.support.Units
import com.silentbit.thingshubmobile.ui.view.InitSelecDataserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var dataStoreManager : DataStoreManager
    @Inject lateinit var units: Units

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        units.setActivity(this)

        val intent = Intent(this, InitSelecDataserver::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val typeServer = dataStoreManager.loadTypeServer()
            withContext(Dispatchers.Main){
                if (typeServer == "") {
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragDashboard,
                R.id.fragDataServer,
                R.id.fragDevice,
                R.id.fragSensors,
                R.id.fragActuators
            ),
            binding.drawerLayout
        )
        binding.navigationView.setupWithNavController(navController)
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when(destination.id){
                R.id.fragDashboard -> binding.navigationView.setCheckedItem(R.id.item_dashboard)
                R.id.fragDataServer -> binding.navigationView.setCheckedItem(R.id.item_data_server)
                R.id.fragDevice -> binding.navigationView.setCheckedItem(R.id.item_devices)
                R.id.fragSensors -> binding.navigationView.setCheckedItem(R.id.item_sensors)
                R.id.fragActuators -> binding.navigationView.setCheckedItem(R.id.item_actuators)
            }

        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId){
                R.id.item_dashboard -> Navigation.findNavController(this,
                    R.id.fragmentContainerView
                )
                    .navigate(R.id.fragDashboard)
                R.id.item_data_server -> Navigation.findNavController(this,
                    R.id.fragmentContainerView
                )
                    .navigate(R.id.fragDataServer)
                R.id.item_devices -> Navigation.findNavController(this, R.id.fragmentContainerView)
                    .navigate(R.id.fragDevice)
                R.id.item_sensors -> Navigation.findNavController(this, R.id.fragmentContainerView)
                    .navigate(R.id.fragSensors)
                R.id.item_actuators -> Navigation.findNavController(this,
                    R.id.fragmentContainerView
                )
                    .navigate(R.id.fragActuators)
            }

            // Handle menu item selected
            menuItem.isChecked = true
            binding.drawerLayout.close()
            true
        }

    }



}