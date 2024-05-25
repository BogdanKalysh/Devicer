package com.bkalysh.devicer.retrofit.models

import com.google.gson.annotations.SerializedName

data class Device(
    @SerializedName("Firmware")             val firmware: String,
    @SerializedName("InternalIP")           val internalIP: String,
    @SerializedName("LastAliveReported")    val lastAliveReported: String,
    @SerializedName("MacAddress")           val macAddress: String,
    @SerializedName("PK_Account")           val pkAccount: Int,
    @SerializedName("PK_Device")            val pkDevice: Int,
    @SerializedName("PK_DeviceSubType")     val pkDeviceSubType: Int,
    @SerializedName("PK_DeviceType")        val pkDeviceType: Int,
    @SerializedName("Platform")             val platform: String,
    @SerializedName("Server_Account")       val serverAccount: String,
    @SerializedName("Server_Device")        val serverDevice: String,
    @SerializedName("Server_Event")         val serverEvent: String
)