package com.bkalysh.devicer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bkalysh.devicer.databinding.ActivityMainBinding
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

        setupRecyclerView()

        binding.btnReset.setOnClickListener {
            viewModel.reloadDevices()
        }

        lifecycleScope.launch {
            viewModel.getAllDevices().collect { devices ->
                devicesAdapter.devices = devices
            }
        }
        lifecycleScope.launch {
            viewModel.isUpdating.collect {isUpdating ->
                binding.pbMain.visibility = if (isUpdating) View.VISIBLE else View.GONE
                if (isUpdating) disableUI() else enableUI() // Disabling UI while updating devices
            }
        }
    }

    private fun disableUI() {
        window.setFlags(
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
    private fun enableUI() {
        window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun setupRecyclerView() = binding.rvDevices.apply {
        devicesAdapter = DevicesAdapter(this@MainActivity)
        adapter = devicesAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}