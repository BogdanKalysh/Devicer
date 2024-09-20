package com.bkalysh.devicer.retrofit

import com.bkalysh.devicer.retrofit.models.DevicesResponse
import retrofit2.Response
import retrofit2.http.GET

interface DevicesApiImpl : DevicesApi {
    @GET("/devicer_backend/devices.json")
    override suspend fun getDevices(): Response<DevicesResponse>
}