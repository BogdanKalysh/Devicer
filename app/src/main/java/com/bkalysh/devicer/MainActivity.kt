package com.bkalysh.devicer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bkalysh.devicer.databinding.ActivityMainBinding
import com.bkalysh.devicer.viewmodel.DeviceViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<DeviceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.getAllDevices().collect { devices ->
                binding.txtTxt.text = devices.joinToString("\n\n")
            }
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.reloadDevices()
        }
    }
}