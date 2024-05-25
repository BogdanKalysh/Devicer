package com.bkalysh.devicer.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Device(
    @PrimaryKey(autoGenerate = false)
    val pkDevice: Int,
    val name: String,
    val firmware: String,
    val internalIP: String,
    val lastAliveReported: String,
    val macAddress: String,
    val pkAccount: Int,
    val pkDeviceSubType: Int,
    val pkDeviceType: Int,
    val platform: String,
    val serverAccount: String,
    val serverDevice: String,
    val serverEvent: String
)
