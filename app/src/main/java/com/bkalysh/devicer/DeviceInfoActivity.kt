package com.bkalysh.devicer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bkalysh.devicer.databinding.ActivityDeviceInfoBinding

class DeviceInfoActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityDeviceInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}