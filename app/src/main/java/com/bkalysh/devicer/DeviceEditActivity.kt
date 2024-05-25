package com.bkalysh.devicer

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bkalysh.devicer.databinding.ActivityDeviceEditBinding
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.utils.Keys
import com.bkalysh.devicer.utils.mapPlatformToImageResource
import com.bkalysh.devicer.utils.toDevice
import com.bkalysh.devicer.viewmodel.DeviceViewModel
import com.google.gson.JsonSyntaxException
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeviceEditActivity : AppCompatActivity() {
    private val viewModel: DeviceViewModel by viewModel()
    private lateinit var binding: ActivityDeviceEditBinding
    private lateinit var device: Device
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deviceJson = intent.getStringExtra(Keys.DEVICE_KEY_EXTRA)
        deviceJson?.apply {
            try {
                device = deviceJson.toDevice()
                setupDevice()
            } catch (e: JsonSyntaxException) {
                Log.e(DeviceInfoActivity.TAG, "Failed to parse JSON for Device")
                finish()
            }
        }

        binding.btnSave.setOnClickListener {
            updateDevice()
        }
    }

    private fun setupDevice() {
        binding.imgDevice.setImageResource(mapPlatformToImageResource(device.platform))
        binding.etDeviceName.setText(device.name)
        binding.tvSerialNumber.text = "SN: ${device.pkDevice}"
        binding.tvMacAddress.text = "MAC Address: ${device.macAddress}"
        binding.tvFirmware.text = "Firmware: ${device.firmware}"
        binding.tvModel.text = "Model: ${device.platform}"
    }

    private fun updateDevice() {
        val name = binding.etDeviceName.text.toString()
        if (name.isNotEmpty()) {
            val newDevice = device.copy(
                name = name
            )
            viewModel.updateDevice(newDevice)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.name_not_null), Toast.LENGTH_SHORT).show()
        }
    }
}