package com.example.lab1advand.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab1advand.databinding.FragmentReceiverBinding

class ReceiverFragment : Fragment() {

    private var _binding: FragmentReceiverBinding? = null
    private val binding get() = _binding!!

    private lateinit var receiver: BroadcastReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReceiverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isOn = Settings.Global.getInt(context?.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
                    binding.airplaneStatusText.text = if (isOn) "Airplane ON" else "Airplane OFF"
                    Toast.makeText(context, binding.airplaneStatusText.text, Toast.LENGTH_SHORT).show()
                }
            }
        }
        requireActivity().registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
