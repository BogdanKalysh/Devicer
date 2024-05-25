package com.bkalysh.devicer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bkalysh.devicer.retrofit.DevicesApi
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.room.DeviceRepository
import com.bkalysh.devicer.utils.toRoomDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DeviceViewModel(private val repository: DeviceRepository, private val api: DevicesApi): ViewModel() {
    val isUpdating = MutableStateFlow(false)

    fun getAllDevices(): Flow<List<Device>> {
        return repository.getAllDevicesSorted()
    }

    fun updateDevice(device: Device) {
        viewModelScope.launch {
            repository.upsertDevice(device)
        }
    }

    fun deleteDevice(device: Device) {
        viewModelScope.launch {
            repository.deleteDevice(device)
        }
    }

    fun reloadDevices() {
        viewModelScope.launch {
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
                repository.deleteAllDevices()
                repository.upsertDevices(response.body()!!.devices.mapIndexed { idx, device ->
                    device.toRoomDevice("$DEFAULT_DEVICE_NAME ${idx+1}") })
            }
            isUpdating.value = false
        }
    }

    companion object {
        val TAG: String = DeviceViewModel::class.java.name

        const val DEFAULT_DEVICE_NAME = "Home Number"
    }
}