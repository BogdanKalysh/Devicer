package com.bkalysh.devicer.retrofit

import com.bkalysh.devicer.retrofit.models.DevicesResponse
import retrofit2.Response
import retrofit2.http.GET

interface DevicesApiImpl : DevicesApi {
    @GET("/test_android/items.test")
    override suspend fun getDevices(): Response<DevicesResponse>
}