package com.bkalysh.devicer
import com.bkalysh.devicer.retrofit.models.Device as RetrofitDevice
import com.bkalysh.devicer.room.models.Device as RoomDevice
import com.bkalysh.devicer.utils.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConvertersTest {

    @Test
    fun testRetrofitDeviceToRoomDevice() {
        val retrofitDevice = RetrofitDevice(
            pkDevice = 1,
            firmware = "1.0.0",
            internalIP = "192.168.1.1",
            lastAliveReported = "2023-01-01T00:00:00Z",
            macAddress = "00:11:22:33:44:55",
            pkAccount = 1,
            pkDeviceSubType = 1,
            pkDeviceType = 1,
            platform = "Sercomm G450",
            serverAccount = "account_server",
            serverDevice = "device_server",
            serverEvent = "event_server"
        )

        val roomDevice = retrofitDevice.toRoomDevice("Test Device")

        assertEquals(retrofitDevice.pkDevice, roomDevice.pkDevice)
        assertEquals("Test Device", roomDevice.name)
        assertEquals(retrofitDevice.firmware, roomDevice.firmware)
        assertEquals(retrofitDevice.internalIP, roomDevice.internalIP)
        assertEquals(retrofitDevice.lastAliveReported, roomDevice.lastAliveReported)
        assertEquals(retrofitDevice.macAddress, roomDevice.macAddress)
        assertEquals(retrofitDevice.pkAccount, roomDevice.pkAccount)
        assertEquals(retrofitDevice.pkDeviceSubType, roomDevice.pkDeviceSubType)
        assertEquals(retrofitDevice.pkDeviceType, roomDevice.pkDeviceType)
        assertEquals(retrofitDevice.platform, roomDevice.platform)
        assertEquals(retrofitDevice.serverAccount, roomDevice.serverAccount)
        assertEquals(retrofitDevice.serverDevice, roomDevice.serverDevice)
        assertEquals(retrofitDevice.serverEvent, roomDevice.serverEvent)
    }

    @Test
    fun testRoomDeviceToJsonStringAndBack() {
        val roomDevice = RoomDevice(
            pkDevice = 1,
            name = "Test Device",
            firmware = "1.0.0",
            internalIP = "192.168.1.1",
            lastAliveReported = "2023-01-01T00:00:00Z",
            macAddress = "00:11:22:33:44:55",
            pkAccount = 1,
            pkDeviceSubType = 1,
            pkDeviceType = 1,
            platform = "Sercomm G450",
            serverAccount = "account_server",
            serverDevice = "device_server",
            serverEvent = "event_server"
        )

        val jsonString = roomDevice.toJsonString()
        val convertedDevice = jsonString.toDevice()

        assertEquals(roomDevice, convertedDevice)
    }

    @Test
    fun testMapPlatformToImageResource() {
        assertEquals(R.drawable.vera_plus_big, mapPlatformToImageResource("Sercomm G450"))
        assertEquals(R.drawable.vera_secure_big, mapPlatformToImageResource("Sercomm G550"))
        assertEquals(R.drawable.vera_edge_big, mapPlatformToImageResource("MiCasaVerde VeraLite"))
        assertEquals(R.drawable.vera_edge_big, mapPlatformToImageResource("Sercomm NA900"))
        assertEquals(R.drawable.vera_edge_big, mapPlatformToImageResource("Sercomm NA301"))
        assertEquals(R.drawable.vera_edge_big, mapPlatformToImageResource("Sercomm NA930"))
        assertEquals(R.drawable.vera_edge_big, mapPlatformToImageResource("Unknown Platform"))
    }
}
