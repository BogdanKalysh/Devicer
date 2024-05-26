package com.bkalysh.devicer

import androidx.room.Room
import com.bkalysh.devicer.retrofit.DevicesApiImpl
import com.bkalysh.devicer.retrofit.DevicesApi
import com.bkalysh.devicer.room.DeviceDao
import com.bkalysh.devicer.room.DeviceDatabase
import com.bkalysh.devicer.room.DeviceRepository
import com.bkalysh.devicer.room.DeviceRepositoryImpl
import com.bkalysh.devicer.viewmodel.DeviceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single <DevicesApi> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.server_name))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DevicesApiImpl::class.java)
    }

    single <DeviceDao> {
        Room.databaseBuilder(
            androidContext(),
            DeviceDatabase::class.java,
            androidContext().getString(R.string.database_name)
        ).build().dao
    }
    single <DeviceRepository> {
        DeviceRepositoryImpl(get())
    }

    viewModel {
        DeviceViewModel(get(), get())
    }
}