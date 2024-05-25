package com.bkalysh.devicer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bkalysh.devicer.databinding.ActivityDeviceInfoBinding
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.utils.Keys.DEVICE_KEY_EXTRA
import com.bkalysh.devicer.utils.mapPlatformToImageResource
import com.bkalysh.devicer.utils.toDevice
import com.google.gson.JsonSyntaxException

class DeviceInfoActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityDeviceInfoBinding
    private lateinit var device: Device
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deviceJson = intent.getStringExtra(DEVICE_KEY_EXTRA)
        deviceJson?.apply {
            try {
                device = deviceJson.toDevice()
                setupDevice()
            } catch (e: JsonSyntaxException) {
                Log.e(TAG, "Failed to parse JSON for Device")
                finish()
            }
        }
    }

    private fun setupDevice() {
        binding.imgDevice.setImageResource(mapPlatformToImageResource(device.platform))
        binding.tvDeviceName.text = device.name
        binding.tvSerialNumber.text = "SN: ${device.pkDevice}"
        binding.tvMacAddress.text = "MAC Address: ${device.macAddress}"
        binding.tvFirmware.text = "Firmware: ${device.firmware}"
        binding.tvModel.text = "Model: ${device.platform}"
    }

    companion object {
        val TAG: String = DeviceInfoActivity::class.java.name
    }
}