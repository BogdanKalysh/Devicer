package com.bkalysh.devicer.room

import com.bkalysh.devicer.room.models.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    suspend fun upsertDevices(devices: List<Device>)
    suspend fun upsertDevice(device: Device)
    suspend fun deleteDevice(device: Device)
    suspend fun deleteAllDevices()
    fun getAllDevicesSorted(): Flow<List<Device>>
}