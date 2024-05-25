package com.bkalysh.devicer.utils

import com.bkalysh.devicer.R
import com.bkalysh.devicer.retrofit.models.Device as RetrofitDevice
import com.bkalysh.devicer.room.models.Device as RoomDevice

fun RetrofitDevice.toRoomDevice(name: String): RoomDevice {
    return RoomDevice(
        pkDevice = this.pkDevice,
        name = name,
        firmware = this.firmware,
        internalIP = this.internalIP,
        lastAliveReported = this.lastAliveReported,
        macAddress = this.macAddress,
        pkAccount = this.pkAccount,
        pkDeviceSubType = this.pkDeviceSubType,
        pkDeviceType = this.pkDeviceType,
        platform = this.platform,
        serverAccount = this.serverAccount,
        serverDevice = this.serverDevice,
        serverEvent = this.serverEvent
    )
}

fun mapPlatformToImageResource(platform: String): Int {
    return when (platform) {
        "Sercomm G450" -> R.drawable.vera_plus_big
        "Sercomm G550" -> R.drawable.vera_secure_big
        "MiCasaVerde VeraLite" -> R.drawable.vera_edge_big
        "Sercomm NA900" -> R.drawable.vera_edge_big
        "Sercomm NA301" -> R.drawable.vera_edge_big
        "Sercomm NA930" -> R.drawable.vera_edge_big
        else -> R.drawable.vera_edge_big
    }
}