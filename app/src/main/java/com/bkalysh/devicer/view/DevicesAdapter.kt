package com.bkalysh.devicer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bkalysh.devicer.databinding.ItemDeviceBinding
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.utils.mapPlatformToImageResource

class DevicesAdapter : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            ItemDeviceBinding.inflate(LayoutInflater.from(parent.context),
                parent, false
            ))
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.binding.apply {
            val device = devices[position]
            tvDeviceName.text = device.name
            tvDeviceSerial.text = "SN ${device.pkDevice}"
            imgDevice.setImageResource(mapPlatformToImageResource(device.platform))
        }
    }

    inner class DeviceViewHolder(val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.pkDevice == newItem.pkDevice
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var devices: List<Device>
        get() = differ.currentList
        set(value) { differ.submitList(value) }
}