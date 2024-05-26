package com.bkalysh.devicer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bkalysh.devicer.retrofit.DevicesApi
import com.bkalysh.devicer.room.DeviceRepository
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.viewmodel.DeviceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.time.delay
import java.time.Duration


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeviceViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DeviceViewModel
    private lateinit var repository: DeviceRepository
    private lateinit var api: DevicesApi

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(DeviceRepository::class.java)
        api = mock(DevicesApi::class.java)
        viewModel = DeviceViewModel(repository, api)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `getAllDevices calls repository and returns flow`() = testScope.runTest {
        val devices = listOf(
            Device(1, "Device 1", "1.0.0", "192.168.1.1", "2023-01-01T00:00:00Z", "00:11:22:33:44:55", 1, 1, 1, "Platform 1", "account_server", "device_server", "event_server")
        )
        `when`(repository.getAllDevicesSorted()).thenReturn(flowOf(devices))

        val result = viewModel.getAllDevices()

        result.collect {
            Assert.assertEquals(devices, it)
        }

        verify(repository).getAllDevicesSorted()
    }

    @Test
    fun `updateDevice calls repository`() = testScope.runTest {
        val device = Device(1, "Device 1", "1.0.0", "192.168.1.1", "2023-01-01T00:00:00Z", "00:11:22:33:44:55", 1, 1, 1, "Platform 1", "account_server", "device_server", "event_server")

        viewModel.updateDevice(device)
        delay(Duration.ofMillis(1000))

        verify(repository).upsertDevice(device)
    }

    @Test
    fun `deleteDevice calls repository`() = testScope.runTest {
        val device = Device(1, "Device 1", "1.0.0", "192.168.1.1", "2023-01-01T00:00:00Z", "00:11:22:33:44:55", 1, 1, 1, "Platform 1", "account_server", "device_server", "event_server")

        viewModel.deleteDevice(device)
        delay(Duration.ofMillis(1000))

        verify(repository).deleteDevice(device)
    }

    @Test
    fun `reloadDevices fetches data and updates repository`() = testScope.runTest {
        val retrofitDevice = com.bkalysh.devicer.retrofit.models.Device(
            pkDevice = 1,
            firmware = "1.0.0",
            internalIP = "192.168.1.1",
            lastAliveReported = "2023-01-01T00:00:00Z",
            macAddress = "00:11:22:33:44:55",
            pkAccount = 1,
            pkDeviceSubType = 1,
            pkDeviceType = 1,
            platform = "Platform 1",
            serverAccount = "account_server",
            serverDevice = "device_server",
            serverEvent = "event_server"
        )
        val response = Response.success(com.bkalysh.devicer.retrofit.models.DevicesResponse(listOf(retrofitDevice)))

        `when`(api.getDevices()).thenReturn(response)

        viewModel.reloadDevices()
        delay(Duration.ofMillis(1000))

        verify(api).getDevices()
        verify(repository).deleteAllDevices()
        verify(repository).upsertDevices(anyList())
    }
}
