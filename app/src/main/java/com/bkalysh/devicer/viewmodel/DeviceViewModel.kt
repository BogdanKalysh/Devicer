package com.bkalysh.devicer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bkalysh.devicer.retrofit.DevicesApi
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.room.DeviceRepository
import com.bkalysh.devicer.utils.toRoomDevice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DeviceViewModel(private val repository: DeviceRepository, private val api: DevicesApi): ViewModel() {
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
            val response = try {
                api.getDevices()
            } catch (ex: IOException) {
                Log.e(TAG, "IOException, could not fetch data from server")
                return@launch
            } catch (ex: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                repository.deleteAllDevices()
                repository.upsertDevices(response.body()!!.devices.mapIndexed { idx, device ->
                    device.toRoomDevice("$DEFAULT_DEVICE_NAME $idx") })
            }
        }
    }

    companion object {
        val TAG: String = DeviceViewModel::class.java.name

        const val DEFAULT_DEVICE_NAME = "Home Number"
    }
}