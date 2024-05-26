package com.bkalysh.devicer

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bkalysh.devicer.databinding.DialogFragmentDeviceDeleteBinding
import com.bkalysh.devicer.room.models.Device
import com.bkalysh.devicer.viewmodel.DeviceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeleteDeviceDialogFragment(private val device: Device) : DialogFragment() {
    private lateinit var binding: DialogFragmentDeviceDeleteBinding
    private val viewModel: DeviceViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = DialogFragmentDeviceDeleteBinding.inflate(requireActivity().layoutInflater)
            builder.setView(binding.root)

            val dialog = builder.create()
            binding.btnOk.setOnClickListener {
                viewModel.deleteDevice(device)
                dismiss()
            }
            binding.btnCancel.setOnClickListener {
                dismiss()
            }
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}