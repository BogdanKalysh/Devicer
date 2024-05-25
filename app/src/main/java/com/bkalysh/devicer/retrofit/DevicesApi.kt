package com.bkalysh.devicer.retrofit

import com.bkalysh.devicer.retrofit.models.DevicesResponse
import retrofit2.Response

interface DevicesApi {
    suspend fun getDevices(): Response<DevicesResponse>
}