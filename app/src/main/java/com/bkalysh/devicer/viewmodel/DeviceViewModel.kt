package com.bkalysh.devicer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bkalysh.devicer.retrofit.DevicesApi
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.room.DeviceRepository
import com.bkalysh.devicer.utils.Constants.DEFAULT_DEVICE_NAME
import com.bkalysh.devicer.utils.toRoomDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DeviceViewModel(private val repository: DeviceRepository, private val api: DevicesApi): ViewModel() {
    val isUpdating = MutableStateFlow(false)

    fun getAllDevices(): Flow<List<Device>> {
        Log.d(TAG, "Calling getAllDevices")
        return repository.getAllDevicesSorted()
    }

    fun updateDevice(device: Device) {
        Log.d(TAG, "Calling updateDevice")
        viewModelScope.launch {
            repository.upsertDevice(device)
        }
    }

    fun deleteDevice(device: Device) {
        Log.d(TAG, "Calling deleteDevice")
        viewModelScope.launch {
            repository.deleteDevice(device)
        }
    }

    fun reloadDevices() {
        Log.d(TAG, "Calling reloadDevices")
        viewModelScope.launch {
            Log.d(TAG, "Launched reloadDevices")
            isUpdating.value = true
            val response = try {
                api.getDevices()
            } catch (ex: IOException) {
                Log.e(TAG, "IOException, could not fetch data from server")
                isUpdating.value = false
                return@launch
            } catch (ex: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                isUpdating.value = false
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                Log.i(TAG, "Successfully fetched devices data.")
                repository.deleteAllDevices()
                repository.upsertDevices(response.body()!!.devices.mapIndexed { idx, device ->
                    device.toRoomDevice("$DEFAULT_DEVICE_NAME ${idx+1}")
                })
            }
            isUpdating.value = false
        }
    }

    companion object {
        val TAG: String = DeviceViewModel::class.java.name
    }
}