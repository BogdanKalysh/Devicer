package com.bkalysh.devicer.retrofit.models

import com.google.gson.annotations.SerializedName

data class DevicesResponse(
    @SerializedName("Devices") val devices: List<Device>
)
