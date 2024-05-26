package com.bkalysh.devicer.room

import com.bkalysh.devicer.room.models.Device
import kotlinx.coroutines.flow.Flow

class DeviceRepositoryImpl(private val deviceDao: DeviceDao) : DeviceRepository {
    override suspend fun upsertDevices(devices: List<Device>) {
        deviceDao.upsertAll(devices)
    }

    override suspend fun upsertDevice(device: Device) {
        deviceDao.upsert(device)
    }

    override suspend fun deleteDevice(device: Device) {
        deviceDao.delete(device)
    }

    override suspend fun deleteAllDevices() {
        deviceDao.deleteAll()
    }

    override fun getAllDevicesSorted(): Flow<List<Device>> {
        return deviceDao.getAllSorted()
    }
}