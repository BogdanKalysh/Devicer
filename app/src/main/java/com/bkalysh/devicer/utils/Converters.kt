package com.bkalysh.devicer.utils

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