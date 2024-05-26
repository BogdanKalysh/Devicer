package com.bkalysh.devicer

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bkalysh.devicer.databinding.ActivityDeviceEditBinding
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.utils.Constants
import com.bkalysh.devicer.utils.mapPlatformToImageResource
import com.bkalysh.devicer.utils.setRoundImageView
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

        setRoundImageView(this, binding.imgUser, R.drawable.user_pick)

        val deviceJson = intent.getStringExtra(Constants.DEVICE_KEY_EXTRA)
        deviceJson?.apply {
            try {
                device = deviceJson.toDevice()
                setupDevice()
            } catch (e: JsonSyntaxException) {
                Log.e(TAG, "Failed to parse JSON for Device")
                finish()
            }
        }
        binding.btnSave.setOnClickListener {
            Log.d(TAG, "Save button clicked")
            updateDevice()
        }
        binding.btnCancel.setOnClickListener {
            Log.d(TAG, "Cancel button clicked")
            finish()
        }
        binding.root.setOnClickListener {
            Log.d(TAG, "Clicked away from keyboard. Hiding the keyboard")
            hideKeyboard()
        }
        binding.etDeviceName.requestFocus()
    }

    private fun setupDevice() {
        Log.d(TAG, "Setting up views with device: $device")
        binding.imgDevice.setImageResource(mapPlatformToImageResource(device.platform))
        binding.etDeviceName.setText(device.name)
        binding.tvSerialNumber.text = getString(R.string.device_serial, device.pkDevice)
        binding.tvMacAddress.text = getString(R.string.device_mac_address, device.macAddress)
        binding.tvFirmware.text = getString(R.string.device_firmware, device.firmware)
        binding.tvModel.text = getString(R.string.device_model, device.platform)
    }

    private fun updateDevice() {
        val name = binding.etDeviceName.text.toString()
        if (name.isNotEmpty()) {
            val newDevice = device.copy(
                name = name
            )
            Log.d(TAG, "Updating device: $device")
            viewModel.updateDevice(newDevice)
            finish()
        } else {
            Log.d(TAG, "Device name set to empty. Don't save")
            Toast.makeText(this, getString(R.string.name_not_null), Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    companion object {
        val TAG: String = DeviceEditActivity::class.java.name
    }
}