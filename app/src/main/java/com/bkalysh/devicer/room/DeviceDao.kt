package com.bkalysh.devicer.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bkalysh.devicer.room.models.Device
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Upsert
    suspend fun upsertAll(devices: List<Device>)

    @Upsert
    suspend fun upsert(device: Device)

    @Delete
    suspend fun delete(device: Device)

    @Query("DELETE FROM device")
    suspend fun deleteAllDevices()

    @Query("SELECT * FROM device ORDER BY pkDevice ASC")
    fun getAllSorted(): Flow<List<Device>>


}