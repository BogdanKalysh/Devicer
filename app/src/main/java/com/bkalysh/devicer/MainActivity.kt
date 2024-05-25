package com.bkalysh.devicer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bkalysh.devicer.databinding.ActivityMainBinding
import com.bkalysh.devicer.view.DevicesAdapter
import com.bkalysh.devicer.viewmodel.DeviceViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<DeviceViewModel>()

    private lateinit var devicesAdapter: DevicesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.reloadDevices() // TODO remove

        lifecycleScope.launch {
            viewModel.getAllDevices().collect { devices ->
                devicesAdapter.devices = devices
            }
        }
    }

    private fun setupRecyclerView() = binding.rvDevices.apply {
        devicesAdapter = DevicesAdapter()
        adapter = devicesAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}