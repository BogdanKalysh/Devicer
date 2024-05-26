package com.bkalysh.devicer.room

import android.util.Log
import com.bkalysh.devicer.room.models.Device
import kotlinx.coroutines.flow.Flow

class DeviceRepositoryImpl(private val deviceDao: DeviceDao) : DeviceRepository {
    override suspend fun upsertDevices(devices: List<Device>) {
        Log.d(TAG, "Calling upsertDevices with: $devices")
        deviceDao.upsertAll(devices)
    }

    override suspend fun upsertDevice(device: Device) {
        Log.d(TAG, "Calling upsertDevice with: $device")
        deviceDao.upsert(device)
    }

    override suspend fun deleteDevice(device: Device) {
        Log.d(TAG, "Calling deleteDevice with: $device")
        deviceDao.delete(device)
    }

    override suspend fun deleteAllDevices() {
        Log.d(TAG, "Calling deleteAllDevices")
        deviceDao.deleteAll()
    }

    override fun getAllDevicesSorted(): Flow<List<Device>> {
        Log.d(TAG, "Calling getAllDevicesSorted")
        return deviceDao.getAllSorted()
    }

    companion object {
        val TAG: String = DeviceRepositoryImpl::class.java.name
    }
}