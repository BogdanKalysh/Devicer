package com.bkalysh.devicer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bkalysh.devicer.databinding.ActivityMainBinding
import com.bkalysh.devicer.utils.Constants.FIRST_RUN_KEY
import com.bkalysh.devicer.utils.setRoundImageView
import com.bkalysh.devicer.view.DevicesAdapter
import com.bkalysh.devicer.viewmodel.DeviceViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DeviceViewModel by viewModel()
    private lateinit var devicesAdapter: DevicesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRoundImageView(this, binding.imgUser, R.drawable.user_pick)
        setupRecyclerView()

        binding.btnReset.setOnClickListener {
            Log.d(TAG, "Reset button clicked")
            viewModel.reloadDevices()
        }
        fetchDataOnFirstLaunch()

        lifecycleScope.launch {
            viewModel.getAllDevices().collect { devices ->
                Log.d(TAG, "Devices list updated")
                devicesAdapter.devices = devices
            }
        }
        lifecycleScope.launch {
            viewModel.isUpdating.collect {isUpdating ->
                Log.d(TAG, "Data update started: $isUpdating")
                binding.pbMain.visibility = if (isUpdating) View.VISIBLE else View.GONE
                if (isUpdating) disableUI() else enableUI() // Disabling UI while updating devices
            }
        }
    }

    private fun disableUI() {
        Log.i(TAG, "Disabling activity UI")
        window.setFlags(
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
    private fun enableUI() {
        Log.i(TAG, "Enabling activity UI")
        window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun setupRecyclerView() = binding.rvDevices.apply {
        devicesAdapter = DevicesAdapter(this@MainActivity)
        adapter = devicesAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun fetchDataOnFirstLaunch() {
        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        if (!sharedPref.contains(FIRST_RUN_KEY)) {
            Log.i(TAG, "App is launched first time. Fetching data.")
            viewModel.reloadDevices()
            sharedPref.edit().putBoolean(FIRST_RUN_KEY, true).apply()
        }
    }

    companion object {
        val TAG: String = MainActivity::class.java.name
    }
}