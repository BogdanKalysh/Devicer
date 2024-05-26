package com.bkalysh.devicer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bkalysh.devicer.databinding.ActivityDeviceInfoBinding
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.utils.Constants.DEVICE_KEY_EXTRA
import com.bkalysh.devicer.utils.mapPlatformToImageResource
import com.bkalysh.devicer.utils.setRoundImageView
import com.bkalysh.devicer.utils.toDevice
import com.google.gson.JsonSyntaxException

class DeviceInfoActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityDeviceInfoBinding
    private lateinit var device: Device
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRoundImageView(this, binding.imgUser, R.drawable.user_pick)

        binding.btnCancel.setOnClickListener {
            finish()
        }

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
        Log.d(TAG, "Setting up views with device: $device")
        binding.imgDevice.setImageResource(mapPlatformToImageResource(device.platform))
        binding.tvDeviceName.text = device.name
        binding.tvSerialNumber.text = getString(R.string.device_serial, device.pkDevice)
        binding.tvMacAddress.text = getString(R.string.device_mac_address, device.macAddress)
        binding.tvFirmware.text = getString(R.string.device_firmware, device.firmware)
        binding.tvModel.text = getString(R.string.device_model, device.platform)
    }

    companion object {
        val TAG: String = DeviceInfoActivity::class.java.name
    }
}