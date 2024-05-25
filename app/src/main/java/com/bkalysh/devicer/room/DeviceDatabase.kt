package com.bkalysh.devicer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bkalysh.devicer.room.models.Device

@Database(
    entities = [Device::class],
    version = 1
)
abstract class DeviceDatabase: RoomDatabase()  {
    abstract val dao: DeviceDao
}