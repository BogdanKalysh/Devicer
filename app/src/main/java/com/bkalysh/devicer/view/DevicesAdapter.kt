package com.bkalysh.devicer.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bkalysh.devicer.DeleteDeviceDialogFragment
import com.bkalysh.devicer.DeviceEditActivity
import com.bkalysh.devicer.DeviceInfoActivity
import com.bkalysh.devicer.databinding.ItemDeviceBinding
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.utils.Keys.DEVICE_KEY_EXTRA
import com.bkalysh.devicer.utils.mapPlatformToImageResource
import com.bkalysh.devicer.utils.toJsonString

class DevicesAdapter(private val context: Context) : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {

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
            tvDeviceSerial.text = "SN: ${device.pkDevice}"
            imgDevice.setImageResource(mapPlatformToImageResource(device.platform))

            root.setOnClickListener {
                val intent = Intent(context, DeviceInfoActivity::class.java)
                intent.putExtra(DEVICE_KEY_EXTRA, device.toJsonString())
                context.startActivity(intent)
            }

            btnEdit.setOnClickListener {
                val intent = Intent(context, DeviceEditActivity::class.java)
                intent.putExtra(DEVICE_KEY_EXTRA, device.toJsonString())
                context.startActivity(intent)
            }

            root.setOnLongClickListener {
                DeleteDeviceDialogFragment(device)
                    .show((context as AppCompatActivity).supportFragmentManager, DELETE_DEVICE_DIALOG)
                true
            }
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

    companion object {
        const val DELETE_DEVICE_DIALOG = "DELETE_DEVICE_DIALOG"
    }
}